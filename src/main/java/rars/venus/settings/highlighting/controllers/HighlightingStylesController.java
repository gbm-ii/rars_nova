package rars.venus.settings.highlighting.controllers;

import org.jetbrains.annotations.NotNull;
import rars.settings.HighlightingSettings;
import rars.venus.editors.TokenStyle;
import rars.venus.settings.editor.TreeNodeKey;
import rars.venus.settings.editor.TreePanel;
import rars.venus.settings.editor.views.SyntaxStyleOptionsView;
import rars.venus.settings.highlighting.HighlightingTreePanel;
import rars.venus.settings.highlighting.views.TableView;

import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public final class HighlightingStylesController {
    private final @NotNull HashMap<TreeNodeKey, TokenStyle> styles;
    private final @NotNull HighlightingSettings highlightingSettings;
    private final @NotNull TableView tableView;
    private final @NotNull TreePanel treePanel;
    private final @NotNull SyntaxStyleOptionsView syntaxStyleOptionsView;
    private @NotNull TreeNodeKey selectedKey;

    public HighlightingStylesController(
        final @NotNull HighlightingSettings highlightingSettings,
        final @NotNull TreePanel treePanel,
        final @NotNull SyntaxStyleOptionsView syntaxStyleOptionsView,
        final @NotNull Color baseForeground,
        final @NotNull Color baseBackground,
        final @NotNull TableView tableView
    ) {
        this.highlightingSettings = highlightingSettings;
        this.tableView = tableView;
        this.styles = new HashMap<>(3);
        this.discardSettings();
        this.treePanel = treePanel;
        this.syntaxStyleOptionsView = syntaxStyleOptionsView;
        this.selectedKey = HighlightingTreePanel.TEXT_KEY;
        initializeViews(baseForeground, baseBackground);

    }

    private void initializeViews(
        final @NotNull Color baseForeground,
        final @NotNull Color baseBackground
    ) {
        final ChangeListener tokenStyleChangeListener = e -> {
            final var tokenStyle = getTokenStyleFromView();
            this.styles.put(this.selectedKey, tokenStyle);
            tableView.setHighlightedCellsStyle(tokenStyle);
        };
        List.of(
            syntaxStyleOptionsView.useForeground,
            syntaxStyleOptionsView.foregroundColorButton,
            syntaxStyleOptionsView.useBackground,
            syntaxStyleOptionsView.backgroundColorButton,
            syntaxStyleOptionsView.isBold,
            syntaxStyleOptionsView.isItalic,
            syntaxStyleOptionsView.isUnderline
        ).forEach(component -> component.addChangeListener(tokenStyleChangeListener));
        treePanel.tree.addTreeSelectionListener(event -> {
            final var selectedNode = (DefaultMutableTreeNode) treePanel.tree.getLastSelectedPathComponent();
            if (selectedNode == null) {
                return;
            }
            switch (selectedNode.getUserObject()) {
                case final TreeNodeKey key -> setCurrentKey(key/*styles.get(key)*/, baseForeground, baseBackground);
                case null, default -> { /* ignore */ }
            }
        });
        this.treePanel.tree.setSelectionRow(0);
    }

    private @NotNull TokenStyle getTokenStyleFromView() {
        final var foreground = syntaxStyleOptionsView.useForeground.isSelected()
            ? syntaxStyleOptionsView.foregroundColorButton.getColor()
            : null;
        final var background = syntaxStyleOptionsView.useBackground.isSelected()
            ? syntaxStyleOptionsView.backgroundColorButton.getColor()
            : null;
        final var isBold = syntaxStyleOptionsView.isBold.isSelected();
        final var isItalic = syntaxStyleOptionsView.isItalic.isSelected();
        final var isUnderline = syntaxStyleOptionsView.isUnderline.isSelected();
        return new TokenStyle(foreground, background, isBold, isItalic, isUnderline);
    }

    private void setCurrentKey(
        final @NotNull TreeNodeKey selectedKey,
        final @NotNull Color baseForeground,
        final @NotNull Color baseBackground
    ) {
        this.selectedKey = selectedKey;
        final var style = this.styles.get(selectedKey);
        this.tableView.setHighlightedCellsStyle(style);
        if (style.foreground() != null) {
            syntaxStyleOptionsView.useForeground.setSelected(true);
            syntaxStyleOptionsView.foregroundColorButton.setColor(style.foreground());
        } else {
            syntaxStyleOptionsView.useForeground.setSelected(false);
            syntaxStyleOptionsView.foregroundColorButton.setColor(baseForeground);
        }
        if (style.background() != null) {
            syntaxStyleOptionsView.useBackground.setSelected(true);
            syntaxStyleOptionsView.backgroundColorButton.setColor(style.background());
        } else {
            syntaxStyleOptionsView.useBackground.setSelected(false);
            syntaxStyleOptionsView.backgroundColorButton.setColor(baseBackground);
        }
        syntaxStyleOptionsView.isBold.setSelected(style.isBold());
        syntaxStyleOptionsView.isItalic.setSelected(style.isItalic());
        syntaxStyleOptionsView.isUnderline.setSelected(style.isUnderline());
    }

    public void applySettings() {
        this.highlightingSettings.setTextSegmentHighlightingStyle(this.styles.get(HighlightingTreePanel.TEXT_KEY));
        this.highlightingSettings.setDataSegmentHighlightingStyle(this.styles.get(HighlightingTreePanel.DATA_KEY));
        this.highlightingSettings.setRegisterHighlightingStyle(this.styles.get(HighlightingTreePanel.REGISTERS_KEY));
        this.highlightingSettings.applySettings();
    }

    public void discardSettings() {
        this.styles.put(HighlightingTreePanel.TEXT_KEY, highlightingSettings.getTextSegmentHighlightingStyle());
        this.styles.put(HighlightingTreePanel.DATA_KEY, highlightingSettings.getDataSegmentHighlightingStyle());
        this.styles.put(HighlightingTreePanel.REGISTERS_KEY, highlightingSettings.getRegisterHighlightingStyle());
    }
}
