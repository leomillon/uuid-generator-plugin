package com.github.leomillon.uuidgenerator.action

import com.github.leomillon.uuidgenerator.DisplayMessageUtils
import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.github.leomillon.uuidgenerator.parser.findIdPlaceholders
import com.github.leomillon.uuidgenerator.parser.textRange
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.MessageType

/**
 * Replace found template id template placeholders in selection by new ones action.
 *
 * @author Léo Millon
 */
class ReplacePlaceholdersInSelectionAction : AnAction() {

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
                        ?.findIdPlaceholders()
                        .orEmpty()
                        .map { (placeholder, range) ->
                            placeholder to range.textRange(caret.selectionStart)
                        }
                }
                .groupBy({ (placeholder, _) -> placeholder }) { (_, range) -> editor.document.createRangeMarker(range) }
                .onEach { (placeholder, ranges) ->
                    val idProducer: () -> String = placeholder.idType.idGenerator

                    val targetId: () -> String = placeholder.label
                        ?.let {
                            val reusedId = idProducer.invoke()
                            ({ reusedId })
                        }
                        ?: idProducer

                    ranges
                        .filter { it.isValid }
                        .forEach { range ->
                            EditorDocumentUtils.replaceTextAtRange(
                                editor,
                                range,
                                targetId()
                            )
                        }
                }
                .count()
                .also { distinctCount ->
                    DisplayMessageUtils.displayMessage(
                        "$distinctCount distinct placeholders have been replaced",
                        project,
                        type = MessageType.INFO
                    )
                }
        }
    }
}
