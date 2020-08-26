package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.DisplayMessageUtils
import com.github.leomillon.uuidgenerator.InvalidFormatException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType

/**
 * Action that provide selected text(s).
 *
 * @author Léo Millon
 */
abstract class WithSelectedTextsAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val project = anActionEvent.getData(CommonDataKeys.PROJECT)
        val editor = anActionEvent.getData(CommonDataKeys.EDITOR)

        if (project == null || editor == null) {
            return
        }

        WriteCommandAction.runWriteCommandAction(project) {
            val errorMessages = mutableListOf<String>()
            editor.caretModel.allCarets
                .filter { it.hasSelection() }
                .forEach { caret ->
                    val selectedText = caret.selectedText ?: return@forEach
                    try {
                        actionOnSelectedText(caret, selectedText)
                    } catch (e: InvalidFormatException) {
                        e.message?.let { errorMessages.add(it) }
                    }
                }
            if (errorMessages.isNotEmpty()) {
                displayErrorMessages(errorMessages, project)
            }
        }
    }

    abstract fun actionOnSelectedText(caret: Caret, selectedText: String)

    private fun displayErrorMessages(messages: Collection<String>, project: Project) {
        messages.joinToString(separator = "<br>")
            .run {
                DisplayMessageUtils.displayMessage(
                    this,
                    project,
                    type = MessageType.ERROR
                )
            }
    }
}
