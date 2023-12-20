package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irCallConstructor
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.builders.irReturn
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.builders.irVararg
import org.jetbrains.kotlin.ir.builders.typeOperator
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.util.isAnnotation
import org.jetbrains.kotlin.ir.util.isSuspend
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class SwordTransformer(
    private val pluginContext: IrPluginContext,
    private val annotationClass: IrClassSymbol,
    private val messageCollector: MessageCollector,
) : IrElementTransformerVoidWithContext() {

    companion object {
        private const val INVOKE_METHOD_NAME = "invoke"
    }

    private val anyNType = pluginContext.irBuiltIns.anyNType
    private val stringType = pluginContext.irBuiltIns.stringType
    private val arrayAnyNType = pluginContext.irBuiltIns.arrayClass.typeWith(anyNType)
    private val emptyArraySymbol =
        pluginContext.referenceFunctions(
            CallableId(
                FqName("kotlin"),
                Name.identifier("emptyArray")
            )
        ).first()
    private val arrayOfSymbol = pluginContext.irBuiltIns.arrayOf

    override fun visitFunctionNew(declaration: IrFunction): IrStatement {
        if (declaration.isSuspend || declaration.isInline || declaration.isExpect || declaration.isExternal) {
            return super.visitFunctionNew(declaration)
        }

        if (declaration is IrSimpleFunction) {
            if (declaration.isInfix || declaration.isTailrec || declaration.isOperator) {
                return super.visitFunctionNew(declaration)
            }
        }

        if (declaration.body == null || !declaration.hasAnnotation(annotationClass)) {
            return super.visitFunctionNew(declaration)
        }

        val param = SwordParam()
        param.hasProxyAnnotation = true

        val irProxyConstructorCall = declaration.annotations.filter {
            it.isAnnotation(SwordNames.PROXY_FQ_NAME)
        }.toList().single()

        val enableParam = irProxyConstructorCall.getValueArgument(0)
        enableParam?.let {
            if (it is IrConst<*>) {
                param.enable = it.value as Boolean
            }
        }

        val handlerParam = irProxyConstructorCall.getValueArgument(1)
        handlerParam?.let {
            if (it is IrConst<*>) {
                param.handler = it.value as String
            }
        }

        val className: String = declaration.className
        val methodName = declaration.name.asString()

        /*messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "className = $className"
        )*/

        if (param.enable && (param.handler.isEmpty() || param.handler.isBlank())) {
            messageCollector.report(
                CompilerMessageSeverity.ERROR,
                "[$className.$methodName]启用代理后请注入`handler`",
            )
        }

        if (param.enable) {
            /*messageCollector.report(
                CompilerMessageSeverity.WARNING,
                declaration.dump()
            )*/

            val sword = irSword(declaration, param, className, methodName)

            /*messageCollector.report(
                CompilerMessageSeverity.WARNING,
                sword.dump()
            )*/

            declaration.body = sword

            /*messageCollector.report(
                CompilerMessageSeverity.WARNING,
                declaration.dump()
            )*/
        }

        return super.visitFunctionNew(declaration)
    }

    private fun irSword(
        function: IrFunction,
        param: SwordParam,
        className: String,
        methodName: String,
    ): IrBlockBody {
        return DeclarationIrBuilder(pluginContext, function.symbol).irBlockBody {

            val handlerConstructorSymbol =
                pluginContext.referenceConstructors(ClassId.topLevel(FqName(param.handler)))
                    .single {
                        it.owner.valueParameters.isEmpty()
                    }

            val invokeSymbol =
                pluginContext.referenceFunctions(
                    CallableId(
                        FqName(param.handler),
                        Name.identifier(INVOKE_METHOD_NAME)
                    )
                )
                    .single {
                        val valueParameters = it.owner.valueParameters
                        valueParameters.size == 3 &&
                                valueParameters[0].type == stringType &&
                                valueParameters[1].type == stringType &&
                                valueParameters[2].type == arrayAnyNType
                    }

            val invokeCall = irCall(invokeSymbol).apply {
                dispatchReceiver = irCallConstructor(handlerConstructorSymbol, emptyList())
                putValueArgument(0, irString(className))
                putValueArgument(1, irString(methodName))
                putValueArgument(2, irSwordArrayParams(function))
            }

            +irReturn(
                typeOperator(
                    resultType = function.returnType,
                    argument = invokeCall,
                    typeOperator = IrTypeOperator.CAST,
                    typeOperand = function.returnType
                )
            )
        }
    }

    private fun IrBuilderWithScope.irSwordArrayParams(function: IrFunction): IrCall {
        val parameters = function.valueParameters
        return if (parameters.isEmpty()) {
            irCall(emptyArraySymbol).also {
                it.putTypeArgument(0, anyNType)
            }
        } else {
            irCall(arrayOfSymbol).also {
                val expressions = parameters.map { parameter -> irGet(parameter) }
                it.putValueArgument(0, irVararg(anyNType, expressions))
            }
        }
    }
}