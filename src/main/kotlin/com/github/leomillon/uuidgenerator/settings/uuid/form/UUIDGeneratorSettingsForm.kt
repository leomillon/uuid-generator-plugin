package com.github.leomillon.uuidgenerator.settings

import com.github.leomillon.uuidgenerator.UUIDGenerator
import com.github.leomillon.uuidgenerator.settings.uuid.UUIDGeneratorSettings
import java.util.*
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JRadioButton

class UUIDGeneratorSettingsForm {

    private val uuidPreview = UUID.fromString("242e6506-710e-4af9-8b3d-dfcfad4bc9a6")

    private var panel: JPanel? = null
    private var previewValue: JLabel? = null
    private var lowerCaseRadioButton: JRadioButton? = null
    private var upperCaseRadioButton: JRadioButton? = null
    private var withDashesRadioButton: JRadioButton? = null
    private var withoutDashesRadioButton: JRadioButton? = null
    private var longSizeRadioButton: JRadioButton? = null
    private var shortSizeRadioButton: JRadioButton? = null

    private val settings: UUIDGeneratorSettings = UUIDGeneratorSettings.instance

    init {
        loadSettings()
    }

    fun loadSettings() {
        lowerCaseRadioButton?.isSelected = settings.lowerCased
        upperCaseRadioButton?.isSelected = !settings.lowerCased
        withDashesRadioButton?.isSelected = settings.withDashes
        withoutDashesRadioButton?.isSelected = !settings.withDashes
        longSizeRadioButton?.isSelected = settings.longSize
        shortSizeRadioButton?.isSelected = !settings.longSize

        sequenceOf(
            lowerCaseRadioButton,
            upperCaseRadioButton,
            withDashesRadioButton,
            withoutDashesRadioButton,
            longSizeRadioButton,
            shortSizeRadioButton
        )
            .filterNotNull()
            .forEach { it.addItemListener { updatePreview() } }

        updatePreview()
    }

    private fun updatePreview() {
        val previewSettings =
            UUIDGeneratorSettings()
        applyToSettings(previewSettings)
        previewValue?.text = UUIDGenerator.formatUUID(uuidPreview, previewSettings)
    }

    fun applyToSettings(settings: UUIDGeneratorSettings) {
        settings.lowerCased = isLowerCased() ?: true
        settings.withDashes = isWithDashes() ?: true
        settings.longSize = isLongSize() ?: true
    }

    fun component(): JComponent? = panel

    private fun isLowerCased() = lowerCaseRadioButton?.isSelected
    private fun isWithDashes() = withDashesRadioButton?.isSelected
    private fun isLongSize() = longSizeRadioButton?.isSelected

    val isModified: Boolean
        get() = (isLowerCased() != settings.lowerCased
            || isWithDashes() != settings.withDashes
            || isLongSize() != settings.longSize)
}
