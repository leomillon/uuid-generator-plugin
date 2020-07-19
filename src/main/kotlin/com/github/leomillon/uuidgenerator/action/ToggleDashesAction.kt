package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.DisplayMessageUtils
import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.github.leomillon.uuidgenerator.InvalidFormatException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType

/**
 * Action that toggle dashes of a uuid.
 *
 * @author Léo Millon
 */
class ToggleDashesAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val project = anActionEvent.getData(CommonDataKeys.PROJECT)
        val editor = anActionEvent.getData(CommonDataKeys.EDITOR)

        if (project == null || editor == null) {
            return
        }

        WriteCommandAction.runWriteCommandAction(project) {
            val errorMessages = mutableListOf<String>()
            for (caret in editor.caretModel.allCarets) {
                try {
                    toggleDashes(caret)
                } catch (e: InvalidFormatException) {
                    e.message?.let { errorMessages.add(it) }
                }
            }
            if (errorMessages.isNotEmpty()) {
                displayErrorMessages(errorMessages, project)
            }
        }
    }

    private fun displayErrorMessages(messages: Collection<String>, project: Project) {
        messages.asSequence()
            .joinToString(separator = "<br>")
            .run {
                DisplayMessageUtils.displayMessage(
                    this,
                    project,
                    type = MessageType.ERROR
                )
            }
    }

    private fun toggleDashes(caret: Caret) {
        if (caret.hasSelection()) {
            caret.selectedText
                ?.takeIf { it.isNotBlank() }
                ?.run {
                    EditorDocumentUtils.insertTextAtCaret(
                        caret,
                        EditorDocumentUtils.toggleUUIDDashes(this)
                    )
                }
        }
    }
}
