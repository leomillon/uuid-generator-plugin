package com.github.leomillon.uuidgenerator.settings.cuid.form

import com.github.leomillon.uuidgenerator.CUIDGenerator
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDGeneratorSettings
import javax.swing.*

class CUIDGeneratorSettingsForm {

    private var panel: JPanel? = null
    private var previewValue: JLabel? = null
    private var lowerCaseRadioButton: JRadioButton? = null
    private var upperCaseRadioButton: JRadioButton? = null
    private var highlightingCheckbox: JCheckBox? = null

    private val settings: CUIDGeneratorSettings = CUIDGeneratorSettings.instance

    init {
        loadSettings()
    }

    fun loadSettings() {
        lowerCaseRadioButton?.isSelected = settings.lowerCased
        upperCaseRadioButton?.isSelected = !settings.lowerCased
        highlightingCheckbox?.isSelected = settings.codeHighlighting

        sequenceOf(
            lowerCaseRadioButton,
            upperCaseRadioButton
        )
            .filterNotNull()
            .forEach { it.addItemListener { updatePreview() } }

        updatePreview()
    }

    private fun updatePreview() {
        val previewSettings =
            CUIDGeneratorSettings()
        applyToSettings(previewSettings)
        previewValue?.text = CUIDGenerator.generateCUID(previewSettings)
    }

    fun applyToSettings(settings: CUIDGeneratorSettings) {
        settings.lowerCased = isLowerCased() ?: false
        settings.codeHighlighting = codeHighlightingEnabled() ?: true
    }

    fun component(): JComponent? = panel

    private fun isLowerCased() = lowerCaseRadioButton?.isSelected
    private fun codeHighlightingEnabled() = highlightingCheckbox?.isSelected

    val isModified: Boolean
        get() = (isLowerCased() != settings.lowerCased
                || codeHighlightingEnabled() != settings.codeHighlighting)
}
