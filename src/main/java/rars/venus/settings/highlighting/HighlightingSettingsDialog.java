package rars.venus.settings.highlighting;

import org.jetbrains.annotations.NotNull;
import rars.settings.HighlightingSettings;
import rars.venus.settings.highlighting.controllers.HighlightingSettingsController;
import rars.venus.settings.highlighting.views.RightSideView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class HighlightingSettingsDialog extends JDialog {
    public HighlightingSettingsDialog(
        final @NotNull Frame owner,
        final @NotNull Color baseForegroundColor,
        final @NotNull Color baseBackgroundColor,
        final @NotNull Font baseFont,
        final @NotNull HighlightingSettings settings
    ) {
        super(owner, true);
        this.setTitle("Highlighting settings");
        final var rightSideView = new RightSideView(baseForegroundColor, baseBackgroundColor, baseFont);
        final var treePanel = HighlightingTreePanel.create();
        final var mainPanel = new HighlightingSettingsPanel(treePanel, rightSideView);
        new HighlightingSettingsController(
            settings,
            this,
            treePanel,
            rightSideView.syntaxStyleOptionsView,
            mainPanel.bottomRowComponent,
            baseForegroundColor,
            baseBackgroundColor,
            rightSideView.tableView
        );
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent we) {
                setVisible(false);
                dispose();
            }
        });
        this.setLocationRelativeTo(owner);
    }
}
