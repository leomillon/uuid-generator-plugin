package com.github.leomillon.uuidgenerator

import com.intellij.openapi.editor.Caret

object EditorDocumentUtils {

    private val UUID_REGEX = "([0-9a-zA-Z]{8})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{12})".toRegex()

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

    fun toggleUUIDDashes(text: String): String {

        if (!UUID_REGEX.matches(text)) {
            throw InvalidFormatException("$text is not a valid uuid format")
        }

        return if (text.contains("-")) removeDashes(text) else insertDashes(text)
    }

    private fun removeDashes(text: String): String {
        return text.replace("-", "")
    }

    private fun insertDashes(text: String): String {

        val matcher = UUID_REGEX.toPattern().matcher(text)

        matcher.find()
        return (1..matcher.groupCount())
            .asSequence()
            .map { matcher.group(it) }
            .joinToString("-")
    }
}
