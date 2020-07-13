package com.github.leomillon.uuidgenerator.settings.uuid

interface UUIDFormatSettings {

    fun isLowerCased(): Boolean

    fun isWithDashes(): Boolean

    fun isLongSize(): Boolean
}
