package com.github.leomillon.uuidgenerator.action.uuid

import com.github.leomillon.uuidgenerator.UUIDGenerator
import com.github.leomillon.uuidgenerator.action.GenerateRandomAction

/**
 * A random UUID generator action.
 *
 * @author Léo Millon
 */
class GenerateUUIDRandomAction : GenerateRandomAction() {
    override fun generateId(): String = UUIDGenerator.generateUUID()
}
