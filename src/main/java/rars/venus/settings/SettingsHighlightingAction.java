package rars.venus.settings;

import org.jetbrains.annotations.NotNull;
import rars.settings.EditorThemeSettings;
import rars.settings.FontSettings;
import rars.settings.HighlightingSettings;
import rars.venus.GuiAction;
import rars.venus.VenusUI;
import rars.venus.settings.highlighting.HighlightingSettingsDialog;

import java.awt.event.ActionEvent;

public final class SettingsHighlightingAction extends GuiAction {

    private final @NotNull HighlightingSettings highlightingSettings;
    private final @NotNull FontSettings fontSettings;
    private final @NotNull EditorThemeSettings editorThemeSettings;

    public SettingsHighlightingAction(
        final @NotNull VenusUI mainUI,
        final @NotNull HighlightingSettings highlightingSettings,
        final @NotNull FontSettings fontSettings,
        final @NotNull EditorThemeSettings editorThemeSettings
    ) {
        super(
            "Highlighting...",
            null,
            "View and modify Execute Tab highlighting colors",
            null,
            null,
            mainUI
        );
        this.highlightingSettings = highlightingSettings;
        this.fontSettings = fontSettings;
        this.editorThemeSettings = editorThemeSettings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final var baseTheme = this.editorThemeSettings.getCurrentTheme();
        final var editorDialog = new HighlightingSettingsDialog(
            this.mainUI,
            baseTheme.foregroundColor,
            baseTheme.backgroundColor,
            fontSettings.getCurrentFont(),
            highlightingSettings
        );
        editorDialog.setVisible(true);
    }
}
