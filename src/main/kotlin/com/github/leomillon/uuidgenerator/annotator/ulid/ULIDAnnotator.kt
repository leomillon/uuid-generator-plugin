package com.github.leomillon.uuidgenerator.annotator.ulid

import com.github.leomillon.uuidgenerator.parser.ULIDParser
import com.github.leomillon.uuidgenerator.parser.findULIDs
import com.github.leomillon.uuidgenerator.parser.textRange
import com.github.leomillon.uuidgenerator.quickfix.ulid.ULIDRandomQuickFix
import com.github.leomillon.uuidgenerator.settings.ulid.ULIDGeneratorSettings
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import java.time.ZoneId

class ULIDDefaultAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        if (!ULIDGeneratorSettings.instance.codeHighlighting) return

        val rawText = (element as? LeafPsiElement)
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
    rawText.findULIDs().forEach { (matchingValue, range) ->
        val textRange = range.textRange(startOffset)
        val ulidParser = ULIDParser(matchingValue)
        val message = when {
            ulidParser.isValid() -> {
                val ulidInstant = ulidParser.getInstant()
                "ULID Timestamp: ${ulidInstant.epochSecond} (${ulidInstant.atZone(ZoneId.systemDefault())})"
            }
            else -> "ULID"
        }
        holder.newAnnotation(HighlightSeverity.INFORMATION, message)
            .range(textRange)
            .enforcedTextAttributes(DefaultLanguageHighlighterColors.CONSTANT.defaultAttributes)
            .withFix(ULIDRandomQuickFix(textRange))
            .create()
    }
}
