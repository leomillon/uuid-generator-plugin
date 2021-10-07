package com.github.leomillon.uuidgenerator.settings.cuid

import com.github.leomillon.uuidgenerator.settings.cuid.form.CUIDGeneratorSettingsForm
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class CUIDGeneratorConfigurable : Configurable {

    var settingsForm: CUIDGeneratorSettingsForm? = null

    override fun createComponent(): JComponent? {
        settingsForm = settingsForm ?: CUIDGeneratorSettingsForm()
        return settingsForm?.component()
    }

    override fun isModified(): Boolean {
        return settingsForm?.isModified ?: return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings =
            CUIDGeneratorSettings.instance
        settingsForm?.applyToSettings(settings)
    }

    override fun reset() {
        settingsForm?.loadSettings()
    }

    override fun disposeUIResources() {
        settingsForm = null
    }

    @Nls
    override fun getDisplayName() = "CUID Generator"

}
