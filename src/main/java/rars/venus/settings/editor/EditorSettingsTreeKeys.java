package rars.venus.settings.editor;

import org.jetbrains.annotations.NotNull;

public final class EditorSettingsTreeKeys {
    public static final @NotNull TreeNodeKey
        FONT_SETTINGS_KEY = new TreeNodeKey("Font"),
        GENERAL_SCHEME_SETTINGS_KEY = new TreeNodeKey("General"),
        SYNTAX_SETTINGS_KEY = new TreeNodeKey("RISC-V Syntax"),
        OTHER_SETTINGS_KEY = new TreeNodeKey("Other settings"),
        PRESETS_KEY = new TreeNodeKey("Presets");

    private EditorSettingsTreeKeys() {
    }
}
