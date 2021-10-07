package com.github.leomillon.uuidgenerator.quickfix

import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile

class IdPlaceholderQuickFix(
    private val textRange: TextRange,
    private val idGenerator: () -> String
) : BaseIntentionAction(), HighPriorityAction {

    override fun getFamilyName(): String = "Random"

    override fun getText(): String = "Replace with new random value"

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile?): Boolean = true

    override fun invoke(project: Project, editor: Editor, file: PsiFile?) {
        WriteCommandAction.runWriteCommandAction(project) {
            EditorDocumentUtils.replaceTextAtRange(editor, textRange, idGenerator())
        }
    }
}
