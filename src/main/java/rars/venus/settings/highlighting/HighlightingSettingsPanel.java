package rars.venus.settings.highlighting;

import org.jetbrains.annotations.NotNull;
import rars.venus.settings.editor.BottomRowComponent;
import rars.venus.settings.editor.TreePanel;
import rars.venus.settings.highlighting.views.RightSideView;

import javax.swing.*;
import java.awt.*;

public final class HighlightingSettingsPanel extends JPanel {
    public final @NotNull BottomRowComponent bottomRowComponent;
    private final @NotNull RightSideView rightSideView;

    public HighlightingSettingsPanel(
        final @NotNull TreePanel treePanel,
        final @NotNull RightSideView rightSideView
    ) {
        super(new BorderLayout());
        this.rightSideView = rightSideView;
        this.bottomRowComponent = new BottomRowComponent();
        this.bottomRowComponent.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(buildMainPart(treePanel, rightSideView), BorderLayout.CENTER);
        this.add(bottomRowComponent, BorderLayout.SOUTH);
    }

    private static @NotNull JSplitPane buildMainPart(
        final @NotNull TreePanel treePanel,
        final @NotNull RightSideView rightSideView
    ) {
        final var result = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            treePanel,
            rightSideView
        );
        // Make left (tree) narrower by default and let right (text area) dominate resizing
        result.setDividerLocation(0.3);
        result.setResizeWeight(0.0); // extra space goes to the right component on resize
        result.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        result.setDividerSize(2);
        // result.setUI(plainSplitPaneDividerUI);
        return result;
    }
}
