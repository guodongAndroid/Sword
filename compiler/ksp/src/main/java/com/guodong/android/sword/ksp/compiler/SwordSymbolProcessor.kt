package com.guodong.android.sword.ksp.compiler

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate
import com.guodong.android.sword.api.kt.InvocationHandler
import com.guodong.android.sword.api.kt.ProxyHandler
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * Created by guodongAndroid on 2022/8/17.
 */
class SwordSymbolProcessor(
    override val env: SymbolProcessorEnvironment,
) : SymbolProcessor, LoggerMixin {

    private val TAG = SwordSymbolProcessor::class.java.simpleName

    private val mInvocationHandlerName = requireNotNull(InvocationHandler::class.qualifiedName)
    private val mProxyHandlerName = requireNotNull(ProxyHandler::class.qualifiedName)

    companion object {
        private const val PARAM_NAME = "paramName"

        private const val PACKAGE_NAME_PARAM_NAME = "sword.pkg"

        private const val DEFAULT_PACKAGE_NAME = "com.guodong.android.sword"
        private const val FILE_NAME = "HandlerFqName"
        private const val CLASS_NAME = "HandlerFqName"
    }

    private var completed = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (completed) {
            logger.warn("$TAG: 本次已完成处理")
            return emptyList()
        }

        logger.warn("$TAG: ${env.kotlinVersion}")

        val mProxyHandlerType =
            resolver.getClassDeclarationByName(mProxyHandlerName)?.asType(emptyList())
        if (mProxyHandlerType == null) {
            logger.warn("$TAG: Not found `ProxyHandler`")
            return emptyList()
        }

        val mInvocationHandlerType =
            resolver.getClassDeclarationByName(mInvocationHandlerName)?.asType(emptyList())
        if (mInvocationHandlerType == null) {
            logger.warn("$TAG: Not found `InvocationHandler`")
            return emptyList()
        }

        val symbols = resolver.getSymbolsWithAnnotation(mProxyHandlerName)
        val invalid = symbols.filter { !it.validate() }.toList()

        val classDeclaration = symbols
            .filter { it.validate() }
            .filterIsInstance<KSClassDeclaration>()
            .filter { declaration ->
                // Class
                val isClass = declaration.classKind == ClassKind.CLASS

                // Public && !Abstract
                val isPublic =
                    Modifier.PUBLIC in declaration.modifiers || declaration.modifiers.isEmpty()
                val isNotAbstract = Modifier.ABSTRACT !in declaration.modifiers

                // 无参构造器
                val primaryConstructor = declaration.primaryConstructor
                val hasNoArgumentConstructor =
                    primaryConstructor == null || primaryConstructor.parameters.isEmpty()

                // 实现`InvocationHandler`接口
                val isImplInvocationHandler =
                    declaration.superTypes.any { ref -> ref.resolve() == mInvocationHandlerType }

                isClass && isPublic && isNotAbstract && hasNoArgumentConstructor && isImplInvocationHandler
            }

//        logger.warn("$TAG: classDeclaration: ${classDeclaration.joinToString { it.qualifiedName?.asString()!! }}")

        val params = mutableListOf<ProxyHandlerParam>()
        for (declaration in classDeclaration) {
            for (annotation in declaration.annotations) {
                for (argument in annotation.arguments) {
                    val name = argument.name?.asString() ?: continue
                    if (name == PARAM_NAME) {
                        val value: String = argument.value as? String ?: continue
                        val fqName: String = declaration.qualifiedName?.asString() ?: continue

                        val paramName = value.ifEmpty { declaration.simpleName.asString() }
                        val param = ProxyHandlerParam(paramName, fqName)
                        params.add(param)
                    }
                }
            }
        }

//        logger.warn("$TAG: params: ${params.joinToString { "${it.paramName} : ${it.fqName}" }}")

        if (params.isNotEmpty()) {
            generate(env.codeGenerator, classDeclaration, params)
        }

        completed = true

        return invalid
    }

    private fun generate(
        codegen: CodeGenerator,
        classDeclaration: Sequence<KSClassDeclaration>,
        params: List<ProxyHandlerParam>
    ) {
        val start = System.currentTimeMillis()
        logger.warn("$TAG: 开始生成Code: $start")

        val propertySpecs = params.map { param ->
            PropertySpec.builder(param.paramName, String::class)
                .addModifiers(KModifier.CONST)
                .initializer("%S", param.fqName)
                .addKdoc("[${param.fqName}]")
                .build()
        }

        val type = TypeSpec.objectBuilder(CLASS_NAME)
            .addKdoc("The class is automatic generated by Sword, don't modify it.\n\n")
            .addKdoc("[${mInvocationHandlerName}]实现类的全限定名")
            .addProperties(propertySpecs)
            .build()

        val pkg = env.options[PACKAGE_NAME_PARAM_NAME]
        val packageName = if (pkg.isNullOrEmpty()) {
            DEFAULT_PACKAGE_NAME
        } else {
            pkg
        }

        logger.warn("$TAG: packageName: $packageName")

        val fileBuilder = FileSpec.builder(packageName, FILE_NAME)
            .addFileComment("The file is automatic generated by Sword, don't modify it.")
            .addType(type)

        val sources =
            classDeclaration.map { it.containingFile }.filterNotNull().toList().toTypedArray()
        val dependencies = Dependencies(true, *sources)
        fileBuilder.build().writeTo(codegen, dependencies)

        val end = System.currentTimeMillis()
        logger.warn("$TAG: 结束生成Code, cost: ${end - start}")
    }
}