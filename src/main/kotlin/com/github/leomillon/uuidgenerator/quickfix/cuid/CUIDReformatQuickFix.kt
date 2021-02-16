package com.github.leomillon.uuidgenerator.quickfix.cuid

import com.github.leomillon.uuidgenerator.EditorDocumentUtils
import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile

class CUIDReformatQuickFix(
    private val source: String,
    private val textRange: TextRange
) : BaseIntentionAction() {

    override fun getFamilyName(): String = "Reformat"

    override fun getText(): String = "Reformat with your CUID settings"

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile?): Boolean = true

    override fun invoke(project: Project, editor: Editor, file: PsiFile?) {
        WriteCommandAction.runWriteCommandAction(project) {
            EditorDocumentUtils.replaceTextAtRange(editor, textRange, EditorDocumentUtils.reformatCUID(source))
        }
    }
}
