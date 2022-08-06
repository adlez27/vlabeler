package com.sdercolin.vlabeler.ui.dialog.customization

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sdercolin.vlabeler.ui.AppRecordStore
import com.sdercolin.vlabeler.ui.AppState
import com.sdercolin.vlabeler.ui.string.Strings
import java.awt.Desktop
import java.io.File

abstract class CustomizableItemManagerDialogState<T : CustomizableItem>(
    val title: Strings,
    val importDialogTitle: Strings,
    val definitionFileNameSuffix: String,
    val directory: File,
    val allowExecution: Boolean,
    protected val appState: AppState,
    protected val appRecordStore: AppRecordStore
) {

    val snackbarHostState = SnackbarHostState()

    private val _items = mutableStateListOf<T>()
    val items: List<T> get() = _items

    var selectedIndex: Int? by mutableStateOf(null)
        private set

    fun loadItems(items: List<T>) {
        _items.clear()
        _items.addAll(items)
    }

    protected abstract fun reload()

    fun toggleItemDisabled() {
        items[requireNotNull(selectedIndex)].toggleDisabled()
    }

    var isShowingFileSelector: Boolean by mutableStateOf(false)
        protected set

    abstract fun saveDisabled()

    val selectedItem get() = selectedIndex?.let { items.getOrNull(it) }

    fun canRemoveCurrentItem(): Boolean = selectedItem?.canRemove ?: false

    fun requestRemoveCurrentItem() {
        appState.confirmIfRemoveCustomizableItem(this, requireNotNull(selectedItem))
    }

    fun removeItem(item: CustomizableItem) {
        require(item.canRemove)
        require(item in items)
        item.remove()
        reload()
    }

    fun canExecuteSelectedItem(): Boolean {
        return allowExecution && (selectedItem?.canExecute() ?: false)
    }

    fun executeSelectedItem() {
        require(canExecuteSelectedItem())
        requireNotNull(selectedItem).execute()
    }

    fun selectItem(index: Int) {
        selectedIndex = index
    }

    fun openDirectory() {
        Desktop.getDesktop().open(directory)
    }

    fun openFileSelectorForNewItem() {
        isShowingFileSelector = true
    }

    suspend fun handleFileSelectorResult(file: File?) {
        isShowingFileSelector = false
        file?.let { addNewItem(it) }
    }

    private suspend fun addNewItem(configFile: File) {
        runCatching { importNewItem(configFile) }.getOrElse {
            snackbarHostState.showSnackbar(it.message.orEmpty())
            return
        }
        reload()
        selectedIndex = items.size
    }

    fun finish() {
        appState.closeCustomizableItemManagerDialog()
    }

    protected abstract suspend fun importNewItem(configFile: File)
}

@Composable
fun rememberCustomizableItemManagerDialogState(
    type: CustomizableItem.Type,
    appState: AppState
) = when (type) {
    CustomizableItem.Type.MacroPlugin -> rememberMacroPluginManagerState(appState)
    CustomizableItem.Type.TemplatePlugin -> TODO()
    CustomizableItem.Type.Labeler -> TODO()
}
