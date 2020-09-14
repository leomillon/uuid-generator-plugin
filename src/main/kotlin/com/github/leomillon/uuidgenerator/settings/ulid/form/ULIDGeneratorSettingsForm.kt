package com.github.leomillon.uuidgenerator.settings.ulid.form

import com.github.leomillon.uuidgenerator.ULIDGenerator
import com.github.leomillon.uuidgenerator.settings.ulid.ULIDGeneratorSettings
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JRadioButton

class ULIDGeneratorSettingsForm {

    private var panel: JPanel? = null
    private var previewValue: JLabel? = null
    private var lowerCaseRadioButton: JRadioButton? = null
    private var upperCaseRadioButton: JRadioButton? = null
    private var standardFormatRadioButton: JRadioButton? = null
    private var guidFormatRadioButton: JRadioButton? = null
    private var highlightingCheckbox: JCheckBox? = null

    private val settings: ULIDGeneratorSettings = ULIDGeneratorSettings.instance

    init {
        loadSettings()
    }

    fun loadSettings() {
        lowerCaseRadioButton?.isSelected = settings.lowerCased
        upperCaseRadioButton?.isSelected = !settings.lowerCased
        standardFormatRadioButton?.isSelected = !settings.guidFormat
        guidFormatRadioButton?.isSelected = settings.guidFormat
        highlightingCheckbox?.isSelected = settings.codeHighlighting

        sequenceOf(
            lowerCaseRadioButton,
            upperCaseRadioButton,
            standardFormatRadioButton,
            guidFormatRadioButton
        )
            .filterNotNull()
            .forEach { it.addItemListener { updatePreview() } }

        updatePreview()
    }

    private fun updatePreview() {
        val previewSettings =
            ULIDGeneratorSettings()
        applyToSettings(previewSettings)
        previewValue?.text = ULIDGenerator.generateULID(previewSettings)
    }

    fun applyToSettings(settings: ULIDGeneratorSettings) {
        settings.lowerCased = isLowerCased() ?: false
        settings.guidFormat = isGuidFormat() ?: false
        settings.codeHighlighting = codeHighlightingEnabled() ?: true
    }

    fun component(): JComponent? = panel

    private fun isLowerCased() = lowerCaseRadioButton?.isSelected
    private fun isGuidFormat() = guidFormatRadioButton?.isSelected
    private fun codeHighlightingEnabled() = highlightingCheckbox?.isSelected

    val isModified: Boolean
        get() = (isLowerCased() != settings.lowerCased
            || isGuidFormat() != settings.guidFormat
            || codeHighlightingEnabled() != settings.codeHighlighting)
}
