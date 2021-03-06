package com.github.leomillon.uuidgenerator.popup.cuid

import com.github.leomillon.uuidgenerator.CUIDGenerator
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDFormatSettings
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

class CUIDGeneratorPopupForm {

    private var panel: JPanel? = null
    private var lowerCaseRadioButton: JRadioButton? = null
    private var upperCaseRadioButton: JRadioButton? = null
    private var numberInputField: JSpinner? = null
    private var separatorInputField: JTextField? = null
    private var prefixInputField: JTextField? = null
    private var suffixInputField: JTextField? = null
    private var resultOutputField: JTextArea? = null

    private var currentIds = listOf<String>()

    private val settings: CUIDGeneratorPopupSettings = CUIDGeneratorPopupSettings.instance

    init {
        loadSettings()
    }

    private fun loadSettings() {
        lowerCaseRadioButton?.isSelected = settings.lowerCased
        upperCaseRadioButton?.isSelected = !settings.lowerCased
        numberInputField?.model = SpinnerNumberModel(settings.numberFieldValue, 1, Int.MAX_VALUE, 1)
        separatorInputField?.text = settings.separatorFieldValue
        prefixInputField?.text = settings.prefixFieldValue
        suffixInputField?.text = settings.suffixFieldValue
        resultOutputField?.text = ""

        sequenceOf(
            lowerCaseRadioButton,
            upperCaseRadioButton
        )
            .filterNotNull()
            .forEach {
                it.addItemListener {
                    updateIds()
                    updatePreview()
                }
            }

        numberInputField?.addChangeListener {
            updateIds()
            updatePreview()
        }

        sequenceOf(
            separatorInputField,
            prefixInputField,
            suffixInputField
        )
            .filterNotNull()
            .forEach {
                it.addKeyListener(object : KeyListener {
                    override fun keyTyped(e: KeyEvent?) = Unit
                    override fun keyPressed(e: KeyEvent?) = Unit
                    override fun keyReleased(e: KeyEvent?) {
                        updatePreview()
                    }
                })
            }

        updateIds()
        updatePreview()
    }

    private fun updateIds() {
        this.currentIds = computeIds(getPreviewSettings())
    }

    private fun updatePreview() {
        val result = currentIds.joinToString(
            separator = unescapedOrEmpty(separatorInputField?.text),
            prefix = unescapedOrEmpty(prefixInputField?.text),
            postfix = unescapedOrEmpty(suffixInputField?.text)
        )

        resultOutputField?.text = result
    }

    private fun getPreviewSettings(): CUIDGeneratorPopupSettings = CUIDGeneratorPopupSettings()
        .also { applyToSettings(it) }

    fun getOutput() = resultOutputField?.text

    private fun unescapedOrEmpty(textInput: String?): String {
        return textInput
            ?.replace("\\n", "\n")
            ?.replace("\\r", "\r")
            ?.replace("\\t", "\t")
            ?: ""
    }

    private fun computeIds(formatSettings: CUIDFormatSettings): List<String> {
        val numberToGenerate = getNumberToGenerate() ?: 1
        return (1..numberToGenerate)
            .asSequence()
            .map { CUIDGenerator.generateCUID(formatSettings) }
            .toList()
    }

    private fun getNumberToGenerate(): Int? {
        return numberInputField?.value
            ?.let {
                if (it is Int) {
                    it
                } else {
                    null
                }
            }
            ?.takeIf { it > 0 }
    }

    fun applyToSettings(settings: CUIDGeneratorPopupSettings) {
        settings.lowerCased = isLowerCased() ?: false
        settings.numberFieldValue = getNumberToGenerate() ?: 1
        settings.separatorFieldValue = separatorInputField?.text ?: ""
        settings.prefixFieldValue = prefixInputField?.text ?: ""
        settings.suffixFieldValue = suffixInputField?.text ?: ""
    }

    fun component(): JComponent? = panel

    private fun isLowerCased() = lowerCaseRadioButton?.isSelected
}
