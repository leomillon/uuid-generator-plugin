package com.github.leomillon.uuidgenerator.action.uuid

import com.github.leomillon.uuidgenerator.DisplayMessageUtils
import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.github.leomillon.uuidgenerator.UUIDGenerator
import com.github.leomillon.uuidgenerator.parser.findUUIDs
import com.github.leomillon.uuidgenerator.parser.textRange
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.MessageType

/**
 * Replace found UUIDs in selection by new ones action.
 *
 * @author Léo Millon
 */
class ReplaceUUIDsInSelectionAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val project = anActionEvent.getData(CommonDataKeys.PROJECT)
        val editor = anActionEvent.getData(CommonDataKeys.EDITOR)

        if (project == null || editor == null) {
            return
        }

        WriteCommandAction.runWriteCommandAction(project) {
            editor.caretModel.allCarets
                .asSequence()
                .filter { it.hasSelection() }
                .flatMap { caret ->
                    caret.selectedText
                        ?.findUUIDs()
                        .orEmpty()
                        .map { (uuid, range) ->
                            uuid to range.textRange(caret.selectionStart)
                        }
                }
                .groupBy({ (uuid, _) -> uuid }) { (_, range) -> range }
                .onEach { (_, ranges) ->
                    val replacement = UUIDGenerator.generateUUID()
                    ranges.forEach { range ->
                        EditorDocumentUtils.replaceTextAtRange(
                            editor,
                            range,
                            replacement
                        )
                    }
                }
                .count()
                .also { distinctCount ->
                    DisplayMessageUtils.displayMessage(
                        "$distinctCount distinct UUIDs have been replaced",
                        project,
                        type = MessageType.INFO
                    )
                }
        }
    }
}
