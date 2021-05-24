package com.github.leomillon.uuidgenerator

import com.github.f4b6a3.ulid.UlidCreator
import com.github.leomillon.uuidgenerator.settings.ulid.ULIDFormatSettings
import com.github.leomillon.uuidgenerator.settings.ulid.ULIDGeneratorSettings

object ULIDGenerator {

    fun generateULID() = generateULID(ULIDGeneratorSettings.instance)

    fun generateULID(settings: ULIDFormatSettings): String = UlidCreator.getUlid()
        .let {
            if (settings.isGuidFormat()) {
                it.toUuid().toString()
            } else {
                it.toString()
            }
        }
        .let { formatULID(it, settings) }

    private fun formatULID(id: String, generatorSettings: ULIDFormatSettings): String =
        id.let {
            if (generatorSettings.isLowerCased()) it.toLowerCase() else it.toUpperCase()
        }
}
