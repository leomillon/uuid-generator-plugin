package com.github.leomillon.uuidgenerator

import assertk.assertThat
import assertk.assertions.matches
import com.github.leomillon.uuidgenerator.parser.CROCKFORD_BASE32_CHARS
import com.github.leomillon.uuidgenerator.settings.ulid.ULIDGeneratorSettings
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ULIDGeneratorTest {

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun expectedRegexBySettings() = listOf(
            Arguments.of(
                ULIDGeneratorSettings(),
                "[${CROCKFORD_BASE32_CHARS}]{26}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = true),
                "[0123456789abcdefghjkmnpqrstvwxyz]{26}".toRegex()
            ),
            Arguments.of(
                settings(guidFormat = true),
                "[0-9A-Z]{8}-[0-9A-Z]{4}-[0-9A-Z]{4}-[0-9A-Z]{4}-[0-9A-Z]{12}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = true, guidFormat = true),
                "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}".toRegex()
            )
        )

        private fun settings(lowerCased: Boolean = false, guidFormat: Boolean = false): ULIDGeneratorSettings {
            val settings = ULIDGeneratorSettings()
            settings.lowerCased = lowerCased
            settings.guidFormat = guidFormat
            return settings
        }
    }

    @ParameterizedTest
    @MethodSource("expectedRegexBySettings")
    fun `should generate uuid for settings`(
        settings: ULIDGeneratorSettings,
        expectedIdRegex: Regex
    ) {
        assertThat(ULIDGenerator.generateULID(settings))
            .matches(expectedIdRegex)
    }
}
