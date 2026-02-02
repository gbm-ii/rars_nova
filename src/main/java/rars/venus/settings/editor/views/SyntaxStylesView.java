package rars.venus.settings.editor.views;

import org.jetbrains.annotations.NotNull;
import rars.settings.TokenSettingKey;

import javax.swing.*;
import java.awt.*;

public final class SyntaxStylesView extends JPanel {

    public final @NotNull SyntaxStyleOptionsView syntaxStyleOptionsView;
    public final @NotNull JList<TokenSettingKey> tokenTypesList;

    public SyntaxStylesView() {
        super(new BorderLayout());

        // Left: list of token types (scrollable)
        this.tokenTypesList = new JList<>(TokenSettingKey.values());
        this.tokenTypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tokenTypesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
            ) {
                final var comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TokenSettingKey key) {
                    setText(key.description);
                }
                return comp;
            }
        });
        final var listScroll = new JScrollPane(this.tokenTypesList);
        // Let the list expand; only set a modest minimum width so it can take most horizontal space
        listScroll.setMinimumSize(new Dimension(120, 0));
        this.add(listScroll, BorderLayout.CENTER);

        this.syntaxStyleOptionsView = new SyntaxStyleOptionsView();

        // Put the options grid on the right with a reasonable fixed width so list gets most space
        final var rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.add(this.syntaxStyleOptionsView, BorderLayout.NORTH);
        // Reserve a reasonable width for the grid; height will follow its contents
        rightWrapper.setPreferredSize(new Dimension(240, 1));
        rightWrapper.setMinimumSize(new Dimension(200, 1));
        this.add(rightWrapper, BorderLayout.EAST);

        // default selection
        if (this.tokenTypesList.getModel().getSize() > 0) {
            this.tokenTypesList.setSelectedIndex(0);
        }
    }
}
