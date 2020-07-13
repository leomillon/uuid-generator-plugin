package com.github.leomillon.uuidgenerator.settings.uuid

import com.github.leomillon.uuidgenerator.settings.UUIDGeneratorSettingsForm
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class UUIDGeneratorConfigurable : Configurable {

    var settingsForm: UUIDGeneratorSettingsForm? = null

    override fun createComponent(): JComponent? {
        settingsForm = settingsForm ?: UUIDGeneratorSettingsForm()
        return settingsForm?.component()
    }

    override fun isModified(): Boolean {
        return settingsForm?.isModified ?: return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings =
            UUIDGeneratorSettings.instance
        settingsForm?.applyToSettings(settings)
    }

    override fun reset() {
        settingsForm?.loadSettings()
    }

    override fun disposeUIResources() {
        settingsForm = null
    }

    @Nls
    override fun getDisplayName() = "UUID Generator"

}
