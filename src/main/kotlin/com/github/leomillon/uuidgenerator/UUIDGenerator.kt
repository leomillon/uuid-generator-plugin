package com.github.leomillon.uuidgenerator

import com.github.leomillon.uuidgenerator.settings.UUIDGeneratorSettings
import java.util.*

object UUIDGenerator {

    fun generateUUID() = generateUUID(UUIDGeneratorSettings.instance)

    fun generateUUID(settings: UUIDGeneratorSettings): String {
        return formatUUID(UUID.randomUUID(), settings)
    }

    fun formatUUID(id: UUID, generatorSettings: UUIDGeneratorSettings): String {

        var formattedId = id.toString()

        if (!generatorSettings.longSize) {
            formattedId = formattedId.substringBefore('-')
        }

        if (!generatorSettings.lowerCased) {
            formattedId = formattedId.toUpperCase()
        }

        if (!generatorSettings.withDashes) {
            formattedId = formattedId.replace("-", "")
        }

        return formattedId
    }
}
