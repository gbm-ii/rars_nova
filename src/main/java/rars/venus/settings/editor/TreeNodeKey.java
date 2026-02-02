package rars.venus.settings.editor;

import org.jetbrains.annotations.NotNull;

public final class TreeNodeKey {
    private final @NotNull String display;

    public TreeNodeKey(@NotNull String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}