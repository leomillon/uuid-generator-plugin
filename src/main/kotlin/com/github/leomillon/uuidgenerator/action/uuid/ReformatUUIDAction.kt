package com.github.leomillon.uuidgenerator.action.uuid

import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.github.leomillon.uuidgenerator.action.WithSelectedTextsAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.util.TextRange

/**
 * Action that reformat a given uuid with the user UUID format settings.
 *
 * @author Léo Millon
 */
class ReformatUUIDAction : WithSelectedTextsAction() {

    override fun actionOnSelectedText(caret: Caret, selectedText: String) {
        if (selectedText.isNotBlank()) {
            EditorDocumentUtils.replaceTextAtRange(
                caret.editor,
                TextRange(caret.selectionStart, caret.selectionEnd),
                EditorDocumentUtils.reformatUUID(selectedText)
            )
        }
    }
}
