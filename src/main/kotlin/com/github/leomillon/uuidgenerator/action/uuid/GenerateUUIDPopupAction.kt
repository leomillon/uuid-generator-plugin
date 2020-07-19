package com.github.leomillon.uuidgenerator.action.uuid

import com.github.leomillon.uuidgenerator.action.GeneratePopupAction
import com.github.leomillon.uuidgenerator.popup.GeneratePopup
import com.github.leomillon.uuidgenerator.popup.uuid.GenerateUUIDPopup

/**
 * Open the UUID generator popup.
 *
 * @author Léo Millon
 */
class GenerateUUIDPopupAction : GeneratePopupAction() {
    override fun createPopup(): GeneratePopup =
        GenerateUUIDPopup()
}
