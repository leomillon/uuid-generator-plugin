package com.github.leomillon.uuidgenerator

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.matches
import com.github.leomillon.uuidgenerator.settings.uuid.UUIDGeneratorSettings
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*

class UUIDGeneratorTest {

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun expectedValuesBySettings() = listOf(
            Arguments.of(
                UUIDGeneratorSettings(),
                "71bc4adb-1b7c-4827-940c-f1c53a3f7766"
            ),
            Arguments.of(
                settings(lowerCased = false),
                "71BC4ADB-1B7C-4827-940C-F1C53A3F7766"
            ),
            Arguments.of(
                settings(withDashes = false),
                "71bc4adb1b7c4827940cf1c53a3f7766"
            ),
            Arguments.of(
                settings(longSize = false),
                "71bc4adb"
            ),
            Arguments.of(
                settings(lowerCased = false, withDashes = false),
                "71BC4ADB1B7C4827940CF1C53A3F7766"
            ),
            Arguments.of(
                settings(lowerCased = false, longSize = false),
                "71BC4ADB"
            ),
            Arguments.of(
                settings(withDashes = false, longSize = false),
                "71bc4adb"
            ),
            Arguments.of(
                settings(lowerCased = false, withDashes = false, longSize = false),
                "71BC4ADB"
            )
        )

        @Suppress("unused")
        @JvmStatic
        fun expectedRegexBySettings() = listOf(
            Arguments.of(
                UUIDGeneratorSettings(),
                "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = false),
                "[0-9A-Z]{8}-[0-9A-Z]{4}-[0-9A-Z]{4}-[0-9A-Z]{4}-[0-9A-Z]{12}".toRegex()
            ),
            Arguments.of(
                settings(withDashes = false),
                "[0-9a-z]{32}".toRegex()
            ),
            Arguments.of(
                settings(longSize = false),
                "[0-9a-z]{8}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = false, withDashes = false),
                "[0-9A-Z]{32}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = false, longSize = false),
                "[0-9A-Z]{8}".toRegex()
            ),
            Arguments.of(
                settings(withDashes = false, longSize = false),
                "[0-9a-z]{8}".toRegex()
            ),
            Arguments.of(
                settings(lowerCased = false, withDashes = false, longSize = false),
                "[0-9A-Z]{8}".toRegex()
            )
        )

        private fun settings(
            lowerCased: Boolean = true,
            withDashes: Boolean = true,
            longSize: Boolean = true
        ): UUIDGeneratorSettings {
            val settings = UUIDGeneratorSettings()
            settings.lowerCased = lowerCased
            settings.withDashes = withDashes
            settings.longSize = longSize
            return settings
        }
    }

    @ParameterizedTest
    @MethodSource("expectedValuesBySettings")
    fun `should format uuid for settings`(
        settings: UUIDGeneratorSettings,
        expectedId: String
    ) {
        val id = UUID.fromString("71bc4adb-1b7c-4827-940c-f1c53a3f7766")

        assertThat(UUIDGenerator.formatUUID(id, settings))
            .isEqualTo(expectedId)
    }

    @ParameterizedTest
    @MethodSource("expectedRegexBySettings")
    fun `should generate uuid for settings`(
        settings: UUIDGeneratorSettings,
        expectedIdRegex: Regex
    ) {
        assertThat(UUIDGenerator.generateUUID(settings))
            .matches(expectedIdRegex)
    }
}
