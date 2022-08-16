package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.org.objectweb.asm.AnnotationVisitor

internal class AnnotationAdapter(
    api: Int,
    annotationVisitor: AnnotationVisitor?,
    private val param: SwordParam
) : AnnotationVisitor(api, annotationVisitor) {

    override fun visit(name: String, value: Any) {
        when (name) {
            SwordParam.PARAM_ENABLE -> param.enable = (value as Boolean)
            SwordParam.PARAM_HANDLER -> param.handler = (value as String)
            else -> {}
        }
    }
}