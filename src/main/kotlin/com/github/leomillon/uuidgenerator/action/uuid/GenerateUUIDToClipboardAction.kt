package com.github.leomillon.uuidgenerator.action.uuid

import com.github.leomillon.uuidgenerator.UUIDGenerator
import com.github.leomillon.uuidgenerator.action.GenerateToClipboardAction

/**
 * Action that generate a UUID into clipboard.
 *
 * @author Léo Millon
 */
class GenerateUUIDToClipboardAction : GenerateToClipboardAction() {
    override fun generateId(): String = UUIDGenerator.generateUUID()
}
