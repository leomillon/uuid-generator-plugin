package com.github.leomillon.uuidgenerator.annotator.cuid

import com.github.leomillon.uuidgenerator.parser.CUID_LENGTH
import com.github.leomillon.uuidgenerator.parser.findCUIDs
import com.github.leomillon.uuidgenerator.parser.textRange
import com.github.leomillon.uuidgenerator.quickfix.cuid.CUIDRandomQuickFix
import com.github.leomillon.uuidgenerator.quickfix.cuid.CUIDReformatQuickFix
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDGeneratorSettings
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement

class CUIDDefaultAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        if (!CUIDGeneratorSettings.instance.codeHighlighting) return

        val rawText = (element as? LeafPsiElement)
            ?.takeIf { it.textLength >= CUID_LENGTH }
            ?.text
            ?.takeIf { it.isNotBlank() }
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
    rawText.findCUIDs().forEach { (matchingValue, range) ->
        val textRange = range.textRange(startOffset)
        holder.newAnnotation(HighlightSeverity.INFORMATION, "CUID")
            .range(textRange)
            .enforcedTextAttributes(DefaultLanguageHighlighterColors.CONSTANT.defaultAttributes)
            .withFix(CUIDRandomQuickFix(textRange))
            .withFix(CUIDReformatQuickFix(matchingValue, textRange))
            .create()
    }
}
