package com.github.leomillon.uuidgenerator.annotator

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import com.github.f4b6a3.ulid.util.UlidUtil
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDGeneratorSettings
import com.github.leomillon.uuidgenerator.settings.ulid.ULIDGeneratorSettings
import com.github.leomillon.uuidgenerator.settings.uuid.UUIDGeneratorSettings
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.json.JsonFileType
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.impl.source.tree.injected.changesHandler.range
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.idea.KotlinFileType
import java.time.ZoneId

private const val targetUUID = "037d596f-0740-48d5-a5ec-8b4948f9e561"
private const val targetULID = "01EGNR5DS9WY7VG9YPT0TXDCA5"
private const val targetCUID = "ckl87igpp000701jp3u7c04r7"
private val ulidDateTime = UlidUtil.extractInstant(targetULID).atZone(ZoneId.systemDefault())

class IDAnnotatorTest : BasePlatformTestCase() {

    override fun tearDown() {
        super.tearDown()
        UUIDGeneratorSettings.instance.also {
            it.codeHighlighting = true
        }
        ULIDGeneratorSettings.instance.also {
            it.codeHighlighting = true
        }
        CUIDGeneratorSettings.instance.also {
            it.codeHighlighting = true
        }
    }

    fun `test should annotate UUIDs, ULIDs and CUIDs in Java code`() {

        // Given
        @Language("Java")
        val code = """
        class SomeJava {
            public static void main(String[] args) {
                System.out.println("Here is some UUID : $targetUUID");
                System.out.println("Here is some ULID : $targetULID");
                System.out.println("Here is some CUID : $targetCUID");
                
                new FoooooooooooooooooooBaaaaaaaaaaa();
            }
            
            public static class FoooooooooooooooooooBaaaaaaaaaaa {
                public void someLongMethodNameeeeeeeeeeeeeee() {}
                public void someotherlongmethodnameeeeeeeeee() {}
            }
            
            /**
             * False positive highlight of CUID #40
             * https://github.com/leomillon/uuid-generator-plugin/issues/40
             */
            public static class TicketTriggerHiredFlowAtCalculator {
            }
            
            /**
             * False positive highlight of CUID #40
             * https://github.com/leomillon/uuid-generator-plugin/issues/40
             */
            public static class ClassToTestCUIDHighlight1 {
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
        assertCUIDHighlight(highlightingResult, 276, 301)
        assertUUIDCount(highlightingResult, 1)
        assertULIDCount(highlightingResult, 1)
        assertCUIDCount(highlightingResult, 1)
    }

    fun `test should annotate UUIDs, ULIDs and CUIDs in Kotlin code`() {

        // Given
        @Language("kotlin")
        val code = """
        fun main(args: Array<String>) {
            println("Here is some UUID : $targetUUID")
            println("Here is some ULID : $targetULID")
            println("Here is some CUID : $targetCUID")
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
        assertCUIDHighlight(highlightingResult, 199, 224)
        assertUUIDCount(highlightingResult, 1)
        assertULIDCount(highlightingResult, 1)
        assertCUIDCount(highlightingResult, 1)
    }

    fun `test should annotate UUIDs, ULIDs and CUIDs in JSON code`() {

        @Language("JSON")
        val code = """
        {
          "someField": "$targetUUID",
          "someOtherField": [
            "$targetULID"
          ],
          "someOtherField2": "$targetCUID"
        }
        """.trimIndent()
        // Given
        myFixture.configureByText(JsonFileType.INSTANCE, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        // When
        val highlightingResult = myFixture.doHighlighting()

        // Then
        assertUUIDHighlight(highlightingResult, 18, 54)
        assertULIDHighlight(highlightingResult, 84, 110)
        assertCUIDHighlight(highlightingResult, 139, 164)
        assertUUIDCount(highlightingResult, 1)
        assertULIDCount(highlightingResult, 1)
        assertCUIDCount(highlightingResult, 1)
    }

    fun `test should not annotate UUIDs, ULIDs and CUIDs in code if highlighting disabled in settings`() {

        // Given
        UUIDGeneratorSettings.instance.also {
            it.codeHighlighting = false
        }
        ULIDGeneratorSettings.instance.also {
            it.codeHighlighting = false
        }
        CUIDGeneratorSettings.instance.also {
            it.codeHighlighting = false
        }
        @Language("JSON")
        val code = """
        {
          "someField": "$targetUUID",
          "someOtherField": [
            "$targetULID"
          ],
          "someOtherField2": "$targetCUID"
        }
        """.trimIndent()
        myFixture.configureByText(JsonFileType.INSTANCE, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        // When
        val highlightingResult = myFixture.doHighlighting()

        // Then
        assertUUIDCount(highlightingResult, 0)
        assertULIDCount(highlightingResult, 0)
        assertCUIDCount(highlightingResult, 0)
    }

    private fun assertUUIDCount(highlightingResult: List<HighlightInfo>, count: Int) {
        assertThat(highlightingResult.filter { it.description == "UUID" }.size).isEqualTo(count)
    }

    private fun assertULIDCount(highlightingResult: List<HighlightInfo>, count: Int) {
        assertThat(highlightingResult.filter { it.description?.startsWith("ULID") ?: false }.size).isEqualTo(count)
    }

    private fun assertCUIDCount(highlightingResult: List<HighlightInfo>, count: Int) {
        assertThat(highlightingResult.filter { it.description?.startsWith("CUID") ?: false }.size).isEqualTo(count)
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

    private fun assertCUIDHighlight(highlightingResult: List<HighlightInfo>, rangeStart: Int, rangeEnd: Int) {
        val highlight = highlightingResult.find { it.text == targetCUID }
        assertThat(highlight).isNotNull()
        with(highlight!!) {
            assertThat(severity).isEqualTo(HighlightSeverity.INFORMATION)
            assertThat(range.startOffset).isEqualTo(rangeStart)
            assertThat(range.endOffset).isEqualTo(rangeEnd)
            assertThat(quickFixActionRanges.map { it.first.action.text }.toList())
                .containsOnly(
                    "Replace with new random CUID",
                    "Reformat with your CUID settings"
                )
        }
    }
}
