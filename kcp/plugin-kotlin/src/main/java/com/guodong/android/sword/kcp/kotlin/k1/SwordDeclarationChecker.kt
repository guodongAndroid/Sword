package com.guodong.android.sword.kcp.kotlin.k1

import com.guodong.android.sword.kcp.kotlin.SwordNames
import com.guodong.android.sword.kcp.kotlin.k1.diagnostic.SwordErrors
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.name.FqNameUnsafe
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext

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
            descriptor.annotations.hasAnnotation(SwordNames.PROXY_FQ_NAME)
        ) {
            val annotation = descriptor.annotations.findAnnotation(SwordNames.PROXY_FQ_NAME)!!
            val allValueArguments = annotation.allValueArguments

            val enable =
                allValueArguments[SwordNames.PROXY_ENABLE_VALUE_ARGUMENT_NAME]!!.value as Boolean
            if (!enable) {
                return
            }

            val handler =
                allValueArguments[SwordNames.PROXY_HANDLER_VALUE_ARGUMENT_NAME]!!.value as String
            val handlerFqNameUnsafe = FqNameUnsafe(handler)
            val classDescriptor =
                context.trace.bindingContext[BindingContext.FQNAME_TO_CLASS_DESCRIPTOR, handlerFqNameUnsafe]
            if (classDescriptor == null) {
                context.trace.report(SwordErrors.NOT_FOUND_INVOCATION_HANDLER.on(declaration, handler))
            }
        }
    }
}