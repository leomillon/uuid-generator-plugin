package com.github.leomillon.uuidgenerator

import assertk.assertThat
import assertk.assertions.matches
import com.github.leomillon.uuidgenerator.parser.CROCKFORD_BASE32_CHARS_UPPER
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDGeneratorSettings
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CUIDGeneratorTest {

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun expectedRegexBySettings() = listOf(
            Arguments.of(
                settings(lowerCased = true),
                "[c][a-z0-9]{24}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = false),
                "[C][A-Z0-9]{24}".toRegex()
            )
        )

        private fun settings(lowerCased: Boolean = false): CUIDGeneratorSettings {
            val settings = CUIDGeneratorSettings()
            settings.lowerCased = lowerCased
            return settings
        }
    }

    @ParameterizedTest
    @MethodSource("expectedRegexBySettings")
    fun `should generate cuid for settings`(
        settings: CUIDGeneratorSettings,
        expectedIdRegex: Regex
    ) {
        assertThat(CUIDGenerator.generateCUID(settings))
            .matches(expectedIdRegex)
    }
}
