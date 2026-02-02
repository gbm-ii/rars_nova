package rars.venus.settings.highlighting;

import org.jetbrains.annotations.NotNull;
import rars.venus.settings.editor.TreeNodeBuilder;
import rars.venus.settings.editor.TreeNodeKey;
import rars.venus.settings.editor.TreePanel;

import javax.swing.*;

import static rars.venus.settings.editor.TreeNodeBuilder.node;

public final class HighlightingTreePanel extends JScrollPane {
    public static final @NotNull TreeNodeKey
        TEXT_KEY = new TreeNodeKey("Text segment"),
        DATA_KEY = new TreeNodeKey("Data segment"),
        REGISTERS_KEY = new TreeNodeKey("Registers");

    private HighlightingTreePanel() {
    }

    public static @NotNull TreePanel create() {
        final var root = TreeNodeBuilder.root(
            node(TEXT_KEY),
            node(DATA_KEY),
            node(REGISTERS_KEY)
        ).collect();
        return new TreePanel(root);
    }
}
