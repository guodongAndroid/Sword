package com.guodong.android.sword.kcp.kotlin.k1

import com.guodong.android.sword.kcp.kotlin.SwordNames
import com.guodong.android.sword.kcp.kotlin.k1.diagnostic.SwordErrors
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.findClassAcrossModuleDependencies
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperInterfaces

/**
 * Created by guodongAndroid on 2023/12/20.
 */
class SwordDeclarationChecker : DeclarationChecker {
    override fun check(
        declaration: KtDeclaration,
        descriptor: DeclarationDescriptor,
        context: DeclarationCheckerContext
    ) {
        if (declaration is KtFunction &&
            descriptor is FunctionDescriptor &&
            descriptor.annotateAsProxyAnnotation()
        ) {
            checkFunction(declaration, descriptor, context)
        }

        if (declaration is KtClass &&
            descriptor is ClassDescriptor &&
            descriptor.kind == ClassKind.CLASS
        ) {
            checkClass(declaration, descriptor, context)
        }
    }

    private fun checkClass(
        declaration: KtClass,
        descriptor: ClassDescriptor,
        context: DeclarationCheckerContext
    ) {
        val implementsInvocationHandlerInterface = descriptor.implementsInvocationHandlerInterface()
        val annotateAsProxyHandlerAnnotation = descriptor.annotateAsProxyHandlerAnnotation()

        when {
            implementsInvocationHandlerInterface && !annotateAsProxyHandlerAnnotation -> {
                context.trace.report(SwordErrors.SUGGESTION_ANNOTATED_PROXY_HANDLER.on(declaration))
            }

            !implementsInvocationHandlerInterface && annotateAsProxyHandlerAnnotation -> {
                context.trace.report(
                    SwordErrors.PROXY_HANDLER_SHOULD_IMPLEMENT_INVOCATION_HANDLER_INTERFACE.on(
                        declaration
                    )
                )
            }
        }
    }

    private fun checkFunction(
        declaration: KtFunction,
        descriptor: FunctionDescriptor,
        context: DeclarationCheckerContext
    ) {
        val annotation =
            descriptor.annotations.findAnnotation(SwordNames.PROXY_FQ_NAME) ?: return
        val allValueArguments = annotation.allValueArguments

        val enable = allValueArguments[SwordNames.PROXY_ENABLE_VALUE_ARGUMENT_NAME]
            ?.value?.safeAs<Boolean>() ?: return
        if (!enable) {
            return
        }

        val handler = allValueArguments[SwordNames.PROXY_HANDLER_VALUE_ARGUMENT_NAME]
            ?.value?.safeAs<String>() ?: return

        if (handler.isEmpty()) {
            context.trace.report(SwordErrors.HANDLER_NOT_ALLOW_EMPTY.on(declaration))
            return
        }

        val handlerClassId = ClassId.topLevel(FqName(handler))
        val classDescriptor =
            context.moduleDescriptor.findClassAcrossModuleDependencies(handlerClassId)
        if (classDescriptor == null) {
            context.trace.report(
                SwordErrors.NOT_FOUND_INVOCATION_HANDLER.on(declaration, handler)
            )
        }
    }

    private inline fun <reified T> Any?.safeAs(): T? = this as? T

    private fun ClassDescriptor.implementsInvocationHandlerInterface() =
        this.getSuperInterfaces().any { it.fqNameSafe == SwordNames.INVOCATION_HANDLER_FQ_NAME }

    private fun DeclarationDescriptor.annotateAsProxyHandlerAnnotation() =
        this.annotations.hasAnnotation(SwordNames.PROXY_HANDLER_FQ_NAME)

    private fun DeclarationDescriptor.annotateAsProxyAnnotation() =
        this.annotations.hasAnnotation(SwordNames.PROXY_FQ_NAME)
}