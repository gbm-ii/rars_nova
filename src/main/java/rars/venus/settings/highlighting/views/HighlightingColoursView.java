package rars.venus.settings.highlighting.views;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rars.venus.settings.editor.ColorPickerButton;

import javax.swing.*;
import java.awt.*;

public final class HighlightingColoursView extends JPanel {
    public final @NotNull ColorPickerButton textColor, dataColor, registerColor;

    public HighlightingColoursView(
        final @NotNull Color initialTextColor,
        final @NotNull Color initialDataColor,
        final @NotNull Color initialRegisterColor
    ) {
        super(new GridBagLayout());

        this.textColor = new ColorPickerButton(initialTextColor);
        this.dataColor = new ColorPickerButton(initialDataColor);
        this.registerColor = new ColorPickerButton(initialRegisterColor);

        final var c = new GridBagConstraints();
        c.gridy = 0;

        final var textLabel = new JLabel("Text segment");
        final var dataLabel = new JLabel("Data segment");
        final var registerLabel = new JLabel("Registers");

        addRow(this, c, textLabel, Box.createHorizontalGlue(), this.textColor);
        addRow(this, c, dataLabel, Box.createHorizontalGlue(), this.dataColor);
        addRow(this, c, registerLabel, Box.createHorizontalGlue(), this.registerColor);
    }

    private static void addRow(
        final @NotNull JPanel panel,
        final @NotNull GridBagConstraints c,
        final @Nullable Component... components
    ) {
        c.gridy += 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        for (final var component : components) {
            if (component != null) {
                panel.add(component, c);
            }
            c.gridx += 1;
        }
    }
}
