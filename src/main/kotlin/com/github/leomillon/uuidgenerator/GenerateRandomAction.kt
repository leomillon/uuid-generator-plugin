package com.github.leomillon.uuidgenerator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import java.util.*

/**
 * A random UUID generator action.
 *
 * @author Léo Millon
 */
class GenerateRandomAction : AnAction() {

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
                    EditorDocumentUtils.insertTextAtCaret(it, UUID.randomUUID().toString())
                }
        }
    }
}
