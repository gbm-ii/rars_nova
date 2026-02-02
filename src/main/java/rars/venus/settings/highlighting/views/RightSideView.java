package rars.venus.settings.highlighting.views;

import org.jetbrains.annotations.NotNull;
import rars.venus.settings.editor.views.SyntaxStyleOptionsView;

import javax.swing.*;
import java.awt.*;

public final class RightSideView extends JPanel {
    public final @NotNull SyntaxStyleOptionsView syntaxStyleOptionsView;
    public final @NotNull TableView tableView;

    public RightSideView(
        @NotNull Color baseForegroundColor,
        @NotNull Color baseBackgroundColor,
        @NotNull Font baseFont
    ) {
        super(new BorderLayout());
        this.syntaxStyleOptionsView = new SyntaxStyleOptionsView();
        this.tableView = new TableView(baseForegroundColor, baseBackgroundColor, baseFont);
        this.add(syntaxStyleOptionsView, BorderLayout.NORTH);
        this.add(tableView, BorderLayout.CENTER);
    }
}
