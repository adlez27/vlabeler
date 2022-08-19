package com.sdercolin.vlabeler.model.action

import com.sdercolin.vlabeler.model.AppConf
import com.sdercolin.vlabeler.model.key.Key
import com.sdercolin.vlabeler.model.key.KeySet
import com.sdercolin.vlabeler.ui.string.Strings
import com.sdercolin.vlabeler.ui.string.string
import com.sdercolin.vlabeler.util.getNullableOrElse

enum class KeyAction(
    val displayedNameSections: List<Strings>,
    val defaultKeySet: KeySet?,
    val isInMenu: Boolean
) : Action {
    NewProject(
        listOf(Strings.MenuFile, Strings.MenuFileNewProject),
        KeySet(Key.N, setOf(Key.Ctrl, Key.Shift)),
        true
    ),
    OpenProject(
        listOf(Strings.MenuFile, Strings.MenuFileOpen),
        KeySet(Key.O, setOf(Key.Ctrl, Key.Shift)),
        true
    ),
    ClearRecentProjects(
        listOf(Strings.MenuFile, Strings.MenuFileOpenRecent, Strings.MenuFileOpenRecentClear),
        null,
        true
    ),
    SaveProject(
        listOf(Strings.MenuFile, Strings.MenuFileSave),
        KeySet(Key.S, setOf(Key.Ctrl)),
        true
    ),
    SaveProjectAs(
        listOf(Strings.MenuFile, Strings.MenuFileSaveAs),
        KeySet(Key.S, setOf(Key.Ctrl, Key.Shift)),
        true
    ),
    ExportProject(
        listOf(Strings.MenuFile, Strings.MenuFileExport),
        KeySet(Key.E, setOf(Key.Ctrl)),
        true
    ),
    InvalidateCaches(
        listOf(Strings.MenuFile, Strings.MenuFileInvalidateCaches),
        KeySet(Key.I, setOf(Key.Ctrl, Key.Shift)),
        true
    ),
    CloseProject(
        listOf(Strings.MenuFile, Strings.MenuFileClose),
        KeySet(Key.W, setOf(Key.Ctrl)),
        true
    ),
    Undo(
        listOf(Strings.MenuEdit, Strings.MenuEditUndo),
        KeySet(Key.Z, setOf(Key.Ctrl)),
        true
    ),
    Redo(
        listOf(Strings.MenuEdit, Strings.MenuEditRedo),
        KeySet(Key.Z, setOf(Key.Ctrl, Key.Shift)),
        true
    ),
    UseToolCursor(
        listOf(Strings.MenuEdit, Strings.MenuEditTools, Strings.MenuEditToolsCursor),
        KeySet(Key.One),
        true
    ),
    UseToolScissors(
        listOf(Strings.MenuEdit, Strings.MenuEditTools, Strings.MenuEditToolsScissors),
        KeySet(Key.Two),
        true
    ),
    RenameCurrentEntry(
        listOf(Strings.MenuEdit, Strings.MenuEditRenameEntry),
        KeySet(Key.R, setOf(Key.Ctrl)),
        true
    ),
    DuplicateCurrentEntry(
        listOf(Strings.MenuEdit, Strings.MenuEditDuplicateEntry),
        KeySet(Key.D, setOf(Key.Ctrl)),
        true
    ),
    RemoveCurrentEntry(
        listOf(Strings.MenuEdit, Strings.MenuEditRemoveEntry),
        KeySet(Key.Delete),
        true
    ),
    ToggleMultipleEditMode(
        listOf(Strings.MenuEdit, Strings.MenuEditMultipleEditMode),
        KeySet(Key.M, setOf(Key.Ctrl)),
        true
    ),
    ToggleMarker(
        listOf(Strings.MenuView, Strings.MenuViewToggleMarker),
        KeySet(Key.Zero, setOf(Key.Ctrl)),
        true
    ),
    ToggleProperties(
        listOf(Strings.MenuView, Strings.MenuViewToggleProperties),
        KeySet(Key.One, setOf(Key.Ctrl)),
        true
    ),
    TogglePinnedEntryList(
        listOf(Strings.MenuView, Strings.MenuViewPinEntryList),
        KeySet(Key.Two, setOf(Key.Ctrl)),
        true
    ),
    ToggleToolbox(
        listOf(Strings.MenuView, Strings.MenuViewToggleToolbox),
        KeySet(Key.Three, setOf(Key.Ctrl)),
        true
    ),
    OpenSampleList(
        listOf(Strings.MenuView, Strings.MenuViewOpenSampleList),
        KeySet(Key.Nine, setOf(Key.Ctrl)),
        true
    ),
    NavigateNextEntry(
        listOf(Strings.MenuNavigate, Strings.MenuNavigateNextEntry),
        KeySet(Key.Down),
        true
    ),
    NavigatePreviousEntry(
        listOf(Strings.MenuNavigate, Strings.MenuNavigatePreviousEntry),
        KeySet(Key.Up),
        true
    ),
    NavigateNextSample(
        listOf(Strings.MenuNavigate, Strings.MenuNavigateNextSample),
        KeySet(Key.Down, setOf(Key.Ctrl)),
        true
    ),
    NavigatePreviousSample(
        listOf(Strings.MenuNavigate, Strings.MenuNavigatePreviousSample),
        KeySet(Key.Up, setOf(Key.Ctrl)),
        true
    ),
    NavigateJumpToEntry(
        listOf(Strings.MenuNavigate, Strings.MenuNavigateJumpToEntry),
        KeySet(Key.G, setOf(Key.Ctrl)),
        true
    ),
    NavigateScrollFit(
        listOf(Strings.MenuNavigate, Strings.MenuNavigateScrollFit),
        KeySet(Key.F),
        true
    ),
    ManageMacroPlugins(
        listOf(Strings.MenuTools, Strings.MenuToolsBatchEdit, Strings.MenuToolsBatchEditManagePlugins),
        KeySet(Key.Semicolon, setOf(Key.Ctrl)),
        true
    ),
    ManageTemplatePlugins(
        listOf(Strings.MenuSettings, Strings.MenuSettingsTemplatePlugins),
        KeySet(Key.Apostrophe, setOf(Key.Ctrl)),
        true
    ),
    Preferences(
        listOf(Strings.MenuSettings, Strings.MenuSettingsPreferences),
        KeySet(Key.Comma, setOf(Key.Ctrl)),
        true
    ),
    ManageLabelers(
        listOf(Strings.MenuSettings, Strings.MenuSettingsLabelers),
        KeySet(Key.Period, setOf(Key.Ctrl)),
        true
    ),
    OpenLogDirectory(listOf(Strings.MenuHelp, Strings.MenuHelpOpenLogDirectory), null, true),
    OpenLatestRelease(listOf(Strings.MenuHelp, Strings.MenuHelpOpenLatestRelease), null, true),
    OpenGitHub(listOf(Strings.MenuHelp, Strings.MenuHelpOpenGitHub), null, true),
    JoinDiscord(listOf(Strings.MenuHelp, Strings.MenuHelpJoinDiscord), null, true),
    ToggleSamplePlayback(
        listOf(Strings.ActionToggleSamplePlayback),
        KeySet(Key.Space, setOf(Key.Shift)),
        false
    ),
    ToggleEntryPlayback(
        listOf(Strings.ActionToggleEntryPlayback),
        KeySet(Key.Space),
        false
    ),
    IncreaseResolution(
        listOf(Strings.ActionIncreaseResolution),
        KeySet(Key.Minus),
        false
    ),
    DecreaseResolution(
        listOf(Strings.ActionDecreaseResolution),
        KeySet(Key.Equals),
        false
    ),
    InputResolution(
        listOf(Strings.ActionInputResolution),
        KeySet(Key.Slash),
        false
    ),
    CancelDialog(
        listOf(Strings.ActionCancelDialog),
        KeySet(Key.Escape),
        false
    );

    override val displayOrder: Int
        get() = values().indexOf(this)

    override val title: String
        get() = displayedNameSections.joinToString(" > ") { string(it) }

    companion object {

        fun getNonMenuKeySets(keymaps: AppConf.Keymaps): List<Pair<KeySet, KeyAction>> = values()
            .filter { !it.isInMenu }
            .mapNotNull { action ->
                val keySet = keymaps.keyActionMap.getNullableOrElse(action) { action.defaultKeySet }
                keySet?.let { it to action }
            }
            .groupBy { it.first.mainKey }
            .flatMap { map -> map.value.sortedByDescending { it.first.subKeys.count() } }
    }
}