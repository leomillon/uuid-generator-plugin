package com.github.leomillon.uuidgenerator

import com.intellij.openapi.editor.Caret
import java.util.*

class EditorDocumentUtils {

    companion object {

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
            return if (text.contains("-")) {
                removeDashes(text)
            } else insertDashes(text)
        }

        private fun removeDashes(text: String): String {
            return text.replace("-".toRegex(), "")
        }

        private fun insertDashes(text: String): String {

            if (text.length != 32) {
                throw InvalidFormatException("$text is not a valid uuid without dashes")
            }

            val result = text.substring(0, 8) +
                "-" +
                text.substring(8, 12) +
                "-" +
                text.substring(12, 16) +
                "-" +
                text.substring(16, 20) +
                "-" +
                text.substring(20)

            try {
                return UUID.fromString(result).toString()
            } catch (e: IllegalArgumentException) {
                throw InvalidFormatException("$text is not a valid uuid without dashes", e)
            }
        }
    }
}
