package com.sdercolin.vlabeler.ui.dialog.plugin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import com.sdercolin.vlabeler.model.BasePlugin
import com.sdercolin.vlabeler.model.Parameter
import com.sdercolin.vlabeler.model.Project
import com.sdercolin.vlabeler.ui.string.Strings
import com.sdercolin.vlabeler.ui.string.string
import com.sdercolin.vlabeler.util.ParamMap
import com.sdercolin.vlabeler.util.toParamMap
import com.sdercolin.vlabeler.util.toUri
import java.awt.Desktop

abstract class BasePluginDialogState(paramMap: ParamMap) {
    abstract val basePlugin: BasePlugin
    protected abstract val savedParamMap: ParamMap?
    abstract val project: Project?
    protected abstract val submit: (ParamMap?) -> Unit
    protected abstract val save: (ParamMap) -> Unit

    val paramDefs: List<Parameter<*>> get() = basePlugin.parameterDefs
    val params = mutableStateListOf(*paramMap.map { it.value }.toTypedArray())
    private val parseErrors = mutableStateListOf(*paramMap.map { false }.toTypedArray())
    val hasParams get() = paramDefs.isNotEmpty()
    val needJsClient get() = paramDefs.any { it is Parameter.EntrySelectorParam }

    private fun getCurrentParamMap() = params.mapIndexed { index, value ->
        paramDefs[index].name to value
    }.toMap().toParamMap()

    fun apply() {
        submit(getCurrentParamMap())
    }

    fun cancel() {
        submit(null)
    }

    @Composable
    fun getLabel(index: Int): String {
        return paramDefs[index].label.get()
    }

    @Composable
    fun getDescription(index: Int): String {
        val description = paramDefs[index].description?.get()
        val range: Pair<String?, String?> = when (val def = paramDefs[index]) {
            is Parameter.FloatParam -> def.min?.toString() to def.max?.toString()
            is Parameter.IntParam -> def.min?.toString() to def.max?.toString()
            else -> null to null
        }
        val suffix = when {
            range.first != null && range.second != null ->
                string(Strings.PluginDialogDescriptionMinMax, range.first, range.second)
            range.first != null ->
                string(Strings.PluginDialogDescriptionMin, range.first)
            range.second != null ->
                string(Strings.PluginDialogDescriptionMax, range.second)
            else -> null
        }
        return listOfNotNull(description, suffix).joinToString("\n")
    }

    fun isValid(index: Int): Boolean = paramDefs[index].check(params[index], project?.labelerConf)

    fun isAllValid() = params.indices.all { isValid(it) } && parseErrors.none { it }

    fun update(index: Int, value: Any) {
        params[index] = value
    }

    fun setParseError(index: Int, isError: Boolean) {
        parseErrors[index] = isError
    }

    fun canReset() = paramDefs.indices.all {
        params[it] == paramDefs[it].defaultValue
    }

    fun reset() {
        paramDefs.indices.forEach {
            params[it] = paramDefs[it].defaultValue
        }
    }

    fun openEmail() {
        Desktop.getDesktop().browse("mailto:${basePlugin.email}".toUri())
    }

    fun openWebsite() {
        val uri = basePlugin.website.takeIf { it.isNotBlank() }?.toUri() ?: return
        Desktop.getDesktop().browse(uri)
    }

    fun canSave(): Boolean {
        val current = runCatching { getCurrentParamMap().toList() }.getOrNull() ?: return false
        val saved = savedParamMap?.toList() ?: return false
        val changed = saved.indices.all { saved[it] == current[it] }.not()
        return changed && isAllValid()
    }

    fun save() {
        save(getCurrentParamMap())
    }

    fun isParamInRow(index: Int): Boolean = when (val param = paramDefs[index]) {
        is Parameter.EntrySelectorParam -> false
        is Parameter.FileParam -> false
        is Parameter.RawFileParam -> false
        is Parameter.StringParam -> param.multiLine.not()
        else -> true
    }
}
