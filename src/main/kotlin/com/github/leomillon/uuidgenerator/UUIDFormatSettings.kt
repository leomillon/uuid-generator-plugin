package com.github.leomillon.uuidgenerator

interface UUIDFormatSettings {

    fun isLowerCased(): Boolean

    fun isWithDashes(): Boolean

    fun isLongSize(): Boolean
}
