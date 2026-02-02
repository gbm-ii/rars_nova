package rars.venus.settings.editor;

import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public final class TreeNodeBuilder {
    private final @NotNull DefaultMutableTreeNode node;

    private TreeNodeBuilder(final @NotNull DefaultMutableTreeNode node) {
        this.node = node;
    }

    public static @NotNull TreeNodeBuilder node(final @NotNull Object data) {
        return new TreeNodeBuilder(new DefaultMutableTreeNode(data));
    }

    public static @NotNull TreeNodeBuilder root(final @NotNull TreeNodeBuilder... children) {
        return new TreeNodeBuilder(new DefaultMutableTreeNode(null)).children(children);
    }

    public @NotNull TreeNodeBuilder children(final TreeNodeBuilder @NotNull ... children) {
        for (final var child : children) {
            this.node.add(child.collect());
        }
        return this;
    }

    public @NotNull MutableTreeNode collect() {
        return node;
    }
}
