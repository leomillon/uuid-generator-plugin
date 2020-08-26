package com.github.leomillon.uuidgenerator

import com.github.leomillon.uuidgenerator.settings.uuid.UUIDFormatSettings
import com.github.leomillon.uuidgenerator.settings.uuid.UUIDGeneratorSettings
import java.util.*



object UUIDGenerator {

    fun generateUUID() =
        generateUUID(UUIDGeneratorSettings.instance)

    fun generateUUID(settings: UUIDGeneratorSettings): String {
        return formatUUID(
            UUID.randomUUID(),
            settings
        )
    }

    fun formatUUID(id: UUID, generatorSettings: UUIDFormatSettings): String {

        var formattedId = id.toString()

        if (!generatorSettings.isLongSize()) {
            formattedId = formattedId.substringBefore('-')
        }

        if (!generatorSettings.isLowerCased()) {
            formattedId = formattedId.toUpperCase()
        }

        if (!generatorSettings.isWithDashes()) {
            formattedId = formattedId.replace("-", "")
        }

        return formattedId
    }
}
