package com.github.leomillon.uuidgenerator.action.ulid

import com.github.leomillon.uuidgenerator.action.GeneratePopupMenuAction
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager

class ULIDGeneratePopupMenuAction : GeneratePopupMenuAction() {
    override fun popupTitle(): String = "Generate ULID"

    override fun resolveActionGroup(): ActionGroup? = ActionManager.getInstance()
        .getAction("ulidgenerator.GenerateActionGroup") as? ActionGroup
}
