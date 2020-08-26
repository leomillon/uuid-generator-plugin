package com.github.leomillon.uuidgenerator

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.util.TextRange
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class EditorDocumentUtilsTest {

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun uuidWithAndWithoutDashes() = listOf(
            Arguments.of(
                "71bc4adb-1b7c-4827-940c-f1c53a3f7766",
                "71bc4adb1b7c4827940cf1c53a3f7766"
            ),
            Arguments.of(
                "71BC4ADB-1B7C-4827-940C-F1C53A3F7766",
                "71BC4ADB1B7C4827940CF1C53A3F7766"
            )
        )
    }

    @ParameterizedTest
    @MethodSource("uuidWithAndWithoutDashes")
    fun `should remove dashes from uuid`(uuidWithDashes: String, uuidWithoutDash: String) {

        // When
        val result = EditorDocumentUtils.toggleUUIDDashes(uuidWithDashes)

        // Then
        assertThat(result).isEqualTo(uuidWithoutDash)
    }

    @ParameterizedTest
    @MethodSource("uuidWithAndWithoutDashes")
    fun `should insert dashes into uuid`(uuidWithDashes: String, uuidWithoutDash: String) {

        // When
        val result = EditorDocumentUtils.toggleUUIDDashes(uuidWithoutDash)

        // Then
        assertThat(result).isEqualTo(uuidWithDashes)
    }

    @Test
    fun `should remove dashes of incomplete uuid`() {

        // When
        val result = EditorDocumentUtils.toggleUUIDDashes("71bc4adb-1b7c4827-940cf1c53a3f7766")

        // Then
        assertThat(result).isEqualTo("71bc4adb1b7c4827940cf1c53a3f7766")
    }

    @Test
    fun `should throw invalid format exception for invalid uuid`() {

        val error = assertThrows<InvalidFormatException> {
            EditorDocumentUtils.toggleUUIDDashes("71bc4adb 1b7c-4827-940c-f1c53a3f7766")
        }
        assertThat(error.message).isEqualTo("71bc4adb 1b7c-4827-940c-f1c53a3f7766 is not a valid uuid format")
    }

    private val UUID_REGEX =
        "([0-9a-zA-Z]{8})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{4})-?([0-9a-zA-Z]{12})".toRegex()

    @Test
    fun `should match uuid index range in text`() {

        val text = "Start: 0fbcf25e-c864-4598-a7e4-43cff6bbbeb4 - 631112f0-26ab-4267-ab06-01183c531fe4  !!"

        val result = UUID_REGEX.findAll(text).map { matchResult ->
            val matchingRange = matchResult.range
            TextRange(matchingRange.first, matchingRange.last)
        }
            .toList()

        assertThat(result).hasSize(2)
    }
}
