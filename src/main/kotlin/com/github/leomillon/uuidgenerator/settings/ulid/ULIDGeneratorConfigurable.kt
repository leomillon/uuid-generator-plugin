package com.github.leomillon.uuidgenerator.settings.ulid

import com.github.leomillon.uuidgenerator.settings.ULIDGeneratorSettingsForm
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class ULIDGeneratorConfigurable : Configurable {

    var settingsForm: ULIDGeneratorSettingsForm? = null

    override fun createComponent(): JComponent? {
        settingsForm = settingsForm ?: ULIDGeneratorSettingsForm()
        return settingsForm?.component()
    }

    override fun isModified(): Boolean {
        return settingsForm?.isModified ?: return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings =
            ULIDGeneratorSettings.instance
        settingsForm?.applyToSettings(settings)
    }

    override fun reset() {
        settingsForm?.loadSettings()
    }

    override fun disposeUIResources() {
        settingsForm = null
    }

    @Nls
    override fun getDisplayName() = "ULID Generator"

}
