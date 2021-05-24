package com.github.leomillon.uuidgenerator

import com.github.leomillon.uuidgenerator.parser.UUIDParser
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDFormatSettings
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDGeneratorSettings
import com.github.leomillon.uuidgenerator.settings.uuid.UUIDFormatSettings
import com.github.leomillon.uuidgenerator.settings.uuid.UUIDGeneratorSettings
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.RangeMarker
import com.intellij.openapi.util.TextRange

val UUID_LONG_REGEX =
    """([0-9a-zA-Z]{8})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{12})""".toRegex()

object EditorDocumentUtils {

    fun insertTextAtCaret(caret: Caret, text: CharSequence) {
        val textLength = text.length
        val start: Int
        val document = caret.editor.document
        if (caret.hasSelection()) {
            start = caret.selectionStart
            val end = caret.selectionEnd

            document.replaceString(start, end, text)
            caret.setSelection(start, start + textLength)
        } else {
            start = caret.offset

            document.insertString(start, text)
        }
        caret.moveToOffset(start + textLength)
    }

    fun replaceTextAtRange(editor: Editor, range: TextRange, text: CharSequence) {
        editor.document.replaceString(range.startOffset, range.endOffset, text)
    }

    fun replaceTextAtRange(editor: Editor, rangeMarker: RangeMarker, text: CharSequence) {
        editor.document.replaceString(rangeMarker.startOffset, rangeMarker.endOffset, text)
    }

    fun reformatUUID(text: String): String = reformatUUID(text, UUIDGeneratorSettings.instance)

    private fun reformatUUID(id: String, generatorSettings: UUIDFormatSettings): String {

        var formattedId = id

        if (!generatorSettings.isLongSize() && !UUIDParser(formattedId).isShort()) {
            formattedId = formattedId.substringBefore('-')
        }

        formattedId = if (generatorSettings.isLowerCased()) {
            formattedId.toLowerCase()
        } else {
            formattedId.toUpperCase()
        }

        formattedId = when {
            generatorSettings.isWithDashes() && !formattedId.contains("-") -> insertDashes(formattedId)
            !generatorSettings.isWithDashes() && formattedId.contains("-") -> removeDashes(formattedId)
            else -> formattedId
        }

        return formattedId
    }

    fun reformatCUID(text: String): String = reformatCUID(text, CUIDGeneratorSettings.instance)

    private fun reformatCUID(id: String, generatorSettings: CUIDFormatSettings): String {
        return if (generatorSettings.isLowerCased()) {
            id.toLowerCase()
        } else {
            id.toUpperCase()
        }
    }

    fun toggleUUIDDashes(text: String): String {
        UUIDParser(text).assertValid()
        return if (text.contains("-")) removeDashes(text) else insertDashes(text)
    }

    private fun removeDashes(text: String): String {
        return text.replace("-", "")
    }

    private fun insertDashes(text: String): String {

        val matcher = UUID_LONG_REGEX.toPattern().matcher(text)

        matcher.find()
        return (1..matcher.groupCount())
            .asSequence()
            .map { matcher.group(it) }
            .joinToString("-")
    }
}
