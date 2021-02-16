package com.github.leomillon.uuidgenerator.parser

const val CUID_LENGTH = 25

val FIND_CUID_REGEX = """[cC][a-zA-Z0-9]{24}""".toRegex()

class CUIDParser(
    private val source: String
) {

    fun isValid(): Boolean = source.length == CUID_LENGTH && (source[0] == 'c' || source[0] == 'C') && source.all {
        it in '0'..'9' || it in 'A'..'Z' || it in 'a'..'z'
    }
}

fun CharSequence.findCUIDs() = FIND_CUID_REGEX.findAll(this)
    .filter { CUIDParser(it.value).isValid() }
    .map { it.value to it.range }
