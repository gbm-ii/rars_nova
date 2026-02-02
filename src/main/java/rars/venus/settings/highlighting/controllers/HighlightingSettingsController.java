package rars.venus.settings.highlighting.controllers;

import org.jetbrains.annotations.NotNull;
import rars.settings.HighlightingSettings;
import rars.venus.settings.editor.BottomRowComponent;
import rars.venus.settings.editor.TreePanel;
import rars.venus.settings.editor.views.SyntaxStyleOptionsView;
import rars.venus.settings.highlighting.HighlightingSettingsDialog;
import rars.venus.settings.highlighting.views.TableView;

import java.awt.*;

public final class HighlightingSettingsController {
    private final @NotNull HighlightingStylesController stylesController;

    public HighlightingSettingsController(
        final @NotNull HighlightingSettings settings,
        final @NotNull HighlightingSettingsDialog dialog,
        final @NotNull TreePanel treePanel,
        final @NotNull SyntaxStyleOptionsView syntaxStyleOptionsView,
        final @NotNull BottomRowComponent bottomRow,
        final @NotNull Color baseForeground,
        final @NotNull Color baseBackground,
        final @NotNull TableView tableView
    ) {
        stylesController = new HighlightingStylesController(
            settings,
            treePanel,
            syntaxStyleOptionsView,
            baseForeground,
            baseBackground,
            tableView
        );
        bottomRow.applyButton.addActionListener(e -> applySettings());
        bottomRow.applyAndCloseButton.addActionListener(e -> {
            applySettings();
            closeDialog(dialog);
        });
        bottomRow.cancelButton.addActionListener(e -> {
            discardSettings();
            closeDialog(dialog);
        });
    }

    private static void closeDialog(final @NotNull HighlightingSettingsDialog dialog) {
        dialog.setVisible(false);
        dialog.dispose();
    }

    private void discardSettings() {
        stylesController.discardSettings();
    }

    private void applySettings() {
        stylesController.applySettings();
    }
}
