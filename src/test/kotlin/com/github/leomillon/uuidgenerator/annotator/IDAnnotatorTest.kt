package com.github.leomillon.uuidgenerator.annotator

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import com.github.f4b6a3.ulid.util.UlidUtil
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.json.JsonFileType
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.impl.source.tree.injected.changesHandler.range
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.idea.KotlinFileType
import java.time.ZoneId

private const val targetUUID = "037d596f-0740-48d5-a5ec-8b4948f9e561"
private const val targetULID = "01EGNR5DS9WY7VG9YPT0TXDCA5"
private val ulidDateTime = UlidUtil.extractInstant(targetULID).atZone(ZoneId.systemDefault())

class IDAnnotatorTest : LightJavaCodeInsightFixtureTestCase() {

    fun `test should annotate UUIDs and ULIDs in Java code`() {

        // Given
        @Language("Java")
        val code = """
        class SomeJava {
            public static void main(String[] args) {
                System.out.println("Here is some UUID : $targetUUID");
                System.out.println("Here is some ULID : $targetULID");
                
                new FoooooooooooooooooooBaaaaaaaaaaa();
            }
            
            public static class FoooooooooooooooooooBaaaaaaaaaaa {
                public void someLongMethodNameeeeeeeeeeeeeee() {}
                public void someotherlongmethodnameeeeeeeeee() {}
            }
        }
        """.trimIndent()
        myFixture.configureByText(JavaFileType.INSTANCE, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        // When
        val highlightingResult = myFixture.doHighlighting()

        // Then
        assertThat(highlightingResult).isNotEmpty()
        assertUUIDHighlight(highlightingResult, 110, 146)
        assertULIDHighlight(highlightingResult, 198, 224)
        assertUUIDCount(highlightingResult)
        assertULIDCount(highlightingResult)
    }

    fun `test should annotate UUIDs and ULIDs in Kotlin code`() {

        // Given
        @Language("kotlin")
        val code = """
        fun main(args: Array<String>) {
            println("Here is some UUID : $targetUUID")
            println("Here is some ULID : $targetULID")
        }
        """.trimIndent()
        myFixture.configureByText(KotlinFileType.INSTANCE, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        // When
        val highlightingResult = myFixture.doHighlighting()

        // Then
        assertThat(highlightingResult).isNotEmpty()
        assertUUIDHighlight(highlightingResult, 65, 101)
        assertULIDHighlight(highlightingResult, 137, 163)
        assertUUIDCount(highlightingResult)
        assertULIDCount(highlightingResult)
    }

    fun `test should annotate UUIDs and ULIDs in JSON code`() {

        // Given
        @Language("JSON")
        val code = """
        {
          "someField": "$targetUUID",
          "someOtherField": [
            "$targetULID"
          ]
        }
        """.trimIndent()
        myFixture.configureByText(JsonFileType.INSTANCE, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        // When
        val highlightingResult = myFixture.doHighlighting()

        // Then
        assertUUIDHighlight(highlightingResult, 18, 54)
        assertULIDHighlight(highlightingResult, 84, 110)
        assertUUIDCount(highlightingResult)
        assertULIDCount(highlightingResult)
    }

    private fun assertUUIDCount(highlightingResult: List<HighlightInfo>) {
        assertThat(highlightingResult.filter { it.description == "UUID" }).hasSize(1)
    }

    private fun assertULIDCount(highlightingResult: List<HighlightInfo>) {
        assertThat(highlightingResult.filter { it.description?.startsWith("ULID") ?: false }).hasSize(1)
    }

    private fun assertUUIDHighlight(highlightingResult: List<HighlightInfo>, rangeStart: Int, rangeEnd: Int) {
        val highlight = highlightingResult.find { it.text == targetUUID }
        assertThat(highlight).isNotNull()
        with(highlight!!) {
            assertThat(description).isEqualTo("UUID")
            assertThat(severity).isEqualTo(HighlightSeverity.INFORMATION)
            assertThat(range.startOffset).isEqualTo(rangeStart)
            assertThat(range.endOffset).isEqualTo(rangeEnd)
            assertThat(quickFixActionRanges.map { it.first.action.text }.toList())
                .containsOnly(
                    "Replace with new random UUID",
                    "Reformat with your UUID settings",
                    "Toggle dashes"
                )
        }
    }

    private fun assertULIDHighlight(highlightingResult: List<HighlightInfo>, rangeStart: Int, rangeEnd: Int) {
        val highlight = highlightingResult.find { it.text == targetULID }
        assertThat(highlight).isNotNull()
        with(highlight!!) {
            assertThat(description).isEqualTo("ULID Timestamp: 1598457820 ($ulidDateTime)")
            assertThat(severity).isEqualTo(HighlightSeverity.INFORMATION)
            assertThat(range.startOffset).isEqualTo(rangeStart)
            assertThat(range.endOffset).isEqualTo(rangeEnd)
            assertThat(quickFixActionRanges.map { it.first.action.text }.toList())
                .containsOnly(
                    "Replace with new random ULID"
                )
        }
    }
}
