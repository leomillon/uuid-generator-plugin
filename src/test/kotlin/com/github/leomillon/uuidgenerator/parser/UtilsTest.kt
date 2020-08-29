package com.github.leomillon.uuidgenerator.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class UtilsTest {

    @ParameterizedTest
    @MethodSource("textWithNumberProvider")
    fun `should validate text with numbers`(input: String, expected: Boolean) {
        assertThat(input.containsAtLeast2Numbers()).isEqualTo(expected)
    }

    @Suppress("unused")
    private fun textWithNumberProvider() = listOf(
        Arguments.of("SomeText", false),
        Arguments.of("SomeText1", false),
        Arguments.of("SomeText12", true),
        Arguments.of("Some1Text2", true),
        Arguments.of("Some1 Text2", false),
        Arguments.of("Some12 Text", true)
    )
}
