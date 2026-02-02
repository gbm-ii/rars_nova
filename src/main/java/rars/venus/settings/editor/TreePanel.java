package rars.venus.settings.editor;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

public final class TreePanel extends JScrollPane {
    public final @NotNull JTree tree;

    public TreePanel(@NotNull TreeNode root) {
        super();
        this.tree = buildTree(root);
        this.setViewportView(this.tree);

    }

    private static @NotNull JTree buildTree(@NotNull TreeNode root) {
        final var tree = new JTree(root);
        tree.setRootVisible(false); // Hide the root node
        tree.setShowsRootHandles(true); // Show expand/collapse icons

        final var cellRenderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        cellRenderer.setLeafIcon(null);
        cellRenderer.setClosedIcon(null);
        cellRenderer.setOpenIcon(null);

        return tree;
    }
}
