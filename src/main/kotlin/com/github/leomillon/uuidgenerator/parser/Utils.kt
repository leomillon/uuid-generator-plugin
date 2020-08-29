package com.github.leomillon.uuidgenerator.parser

import com.intellij.openapi.util.TextRange

fun IntRange.textRange(offset: Int = 0) = this.let {
    TextRange(
        offset + it.first,
        offset + it.last + 1
    )
}

fun String.containsAtLeast2Numbers() = this.contains("""[0-9][^\s]*[0-9]""".toRegex())
