package com.github.leomillon.uuidgenerator.annotator

import com.github.leomillon.uuidgenerator.parser.findIdPlaceholders
import com.github.leomillon.uuidgenerator.parser.textRange
import com.github.leomillon.uuidgenerator.quickfix.IdPlaceholderQuickFix
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement

class IdPlaceholderAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        val rawText = (element as? LeafPsiElement)
            ?.takeIf { it.textLength >= 10 }
            ?.takeIf { it.textContains('#') }
            ?.text
            ?: return

        highlightInText(element, rawText, holder)
    }
}

private fun highlightInText(
    element: PsiElement,
    rawText: String,
    holder: AnnotationHolder
) {
    val startOffset = element.textRange.startOffset
    rawText.findIdPlaceholders().forEach { (placeholder, range) ->
        val textRange = range.textRange(startOffset)
        val message =
            """Random ${placeholder.idType.name} placeholder${placeholder.label?.let { " labeled '$it'" } ?: ""}"""
        holder.newAnnotation(HighlightSeverity.INFORMATION, message)
            .range(textRange)
            .enforcedTextAttributes(DefaultLanguageHighlighterColors.CONSTANT.defaultAttributes)
            .withFix(IdPlaceholderQuickFix(textRange, placeholder.idType.idGenerator))
            .create()
    }
}
