package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.intellij.openapi.editor.Caret

/**
 * Action that toggle dashes of a uuid.
 *
 * @author Léo Millon
 */
class ToggleDashesAction : WithSelectedTextsAction() {

    override fun actionOnSelectedText(caret: Caret, selectedText: String) {
        if (selectedText.isNotBlank()) {
            EditorDocumentUtils.insertTextAtCaret(
                caret,
                EditorDocumentUtils.toggleUUIDDashes(selectedText)
            )
        }
    }
}
