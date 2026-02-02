package rars.venus.settings.editor;

import org.jetbrains.annotations.NotNull;
import rars.venus.settings.editor.controllers.EditorSettingsController;
import rars.venus.settings.editor.views.PanelWithTextAreaView;
import rars.venus.settings.editor.views.PickerCardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static rars.Globals.*;
import static rars.venus.settings.editor.EditorSettingsTreeKeys.*;
import static rars.venus.settings.editor.TreeNodeBuilder.node;

public final class EditorSettingsDialog extends JDialog {
    public EditorSettingsDialog(
        final @NotNull Frame owner,
        final @NotNull String title,
        final boolean modality
    ) {
        super(owner, title, modality);
        final var pickerCardView = new PickerCardView();
        final var panelWithTextAreaView = new PanelWithTextAreaView(pickerCardView);
        final var treePanel = createTreePanel();
        final var mainPanel = new EditorSettingsPanel(treePanel, panelWithTextAreaView);
        new EditorSettingsController(
            mainPanel,
            this,
            treePanel,
            FONT_SETTINGS,
            EDITOR_THEME_SETTINGS,
            OTHER_SETTINGS
        );
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent we) {
                    EditorSettingsDialog.this.setVisible(false);
                    EditorSettingsDialog.this.dispose();
                }
            });
        this.setLocationRelativeTo(owner);
    }

    private static @NotNull TreePanel createTreePanel() {
        final var root = TreeNodeBuilder.root(
            node(FONT_SETTINGS_KEY),
            node("Color scheme").children(
                node(PRESETS_KEY),
                node(GENERAL_SCHEME_SETTINGS_KEY),
                node(SYNTAX_SETTINGS_KEY)
            ),
            node(OTHER_SETTINGS_KEY)
        ).collect();

        final var treePanel = new TreePanel(root);
        // Keep the navigation panel compact vertically
        treePanel.setPreferredSize(new Dimension(200, 300));
        return treePanel;
    }
}
