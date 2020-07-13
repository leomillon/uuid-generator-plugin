package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction

/**
 * A random ID generator action.
 *
 * @author Léo Millon
 */
abstract class GenerateRandomAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val project = anActionEvent.getData(CommonDataKeys.PROJECT)
        val editor = anActionEvent.getData(CommonDataKeys.EDITOR)

        if (project == null || editor == null) {
            return
        }

        //Making the replacement
        WriteCommandAction.runWriteCommandAction(project) {
            editor.caretModel
                .allCarets
                .forEach {
                    EditorDocumentUtils.insertTextAtCaret(
                        it,
                        generateId()
                    )
                }
        }
    }

    abstract fun generateId(): String
}
