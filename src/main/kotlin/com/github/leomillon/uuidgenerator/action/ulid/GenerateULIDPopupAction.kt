package com.github.leomillon.uuidgenerator.action.ulid

import com.github.leomillon.uuidgenerator.action.GeneratePopupAction
import com.github.leomillon.uuidgenerator.popup.GeneratePopup
import com.github.leomillon.uuidgenerator.popup.ulid.GenerateULIDPopup

/**
 * Open the ULID generator popup.
 *
 * @author Léo Millon
 */
class GenerateULIDPopupAction : GeneratePopupAction() {
    override fun createPopup(): GeneratePopup =
        GenerateULIDPopup()
}
