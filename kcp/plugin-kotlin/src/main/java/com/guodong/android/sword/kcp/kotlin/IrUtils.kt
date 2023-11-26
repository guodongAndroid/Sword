package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.util.fileOrNull
import org.jetbrains.kotlin.ir.util.findAnnotation
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.util.isLocal
import org.jetbrains.kotlin.ir.util.parentClassOrNull
import org.jetbrains.kotlin.name.FqName

/**
 * Created by guodongAndroid on 2023/11/26.
 */
private val jvmNameAnnotationFqName = FqName("kotlin.jvm.JvmName")

val IrFunction.className: String
    get() {
        val parentClassOrNull = parentClassOrNull
        val fileOrNull = fileOrNull

        return when {
            isLocal -> {
                val className = buildString {
                    renderParentOfReferencedDeclaration(this@className)
                }

                // 截取第一位的"."
                if (className.isNotEmpty()) {
                    className.substring(1)
                } else {
                    className
                }
            }

            parentClassOrNull != null -> {
                parentClassOrNull.name.asString()
            }

            fileOrNull != null -> {
                val annotations = fileOrNull.annotations
                if (annotations.hasAnnotation(jvmNameAnnotationFqName)) {
                    val annotation = annotations.findAnnotation(jvmNameAnnotationFqName)!!
                    val expression = annotation.getValueArgument(0)
                    if (expression != null && expression is IrConst<*>) {
                        expression.value as String
                    } else {
                        fileOrNull.name
                    }
                } else {
                    fileOrNull.name
                }
            }

            else -> "Unknown"
        }
    }

fun StringBuilder.renderParentOfReferencedDeclaration(declaration: IrDeclaration) {
    val parent = try {
        declaration.parent
    } catch (e: Exception) {
        append("<no parent>")
        return
    }
    when (parent) {
        /*is IrPackageFragment -> {
            val fqn = parent.fqName.asString()
            append(fqn.ifEmpty { "<root>" })
        }*/
        is IrDeclaration -> {
            renderParentOfReferencedDeclaration(parent)
            append('.')
            if (parent is IrDeclarationWithName) {
                append(parent.name)
            }/* else {
                    renderElementNameFallback(parent)
                }*/
        }
        /*else -> renderElementNameFallback(parent)*/
    }
}

/*private fun StringBuilder.renderElementNameFallback(element: Any) {
    append('{')
    append(element.javaClass.simpleName)
    append('}')
}*/