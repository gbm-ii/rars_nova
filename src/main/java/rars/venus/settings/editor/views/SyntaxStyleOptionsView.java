package rars.venus.settings.editor.views;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rars.venus.settings.editor.ColorPickerButton;

import javax.swing.*;
import java.awt.*;

public final class SyntaxStyleOptionsView extends JPanel {
    public final @NotNull JCheckBox isBold, isItalic, isUnderline, useForeground, useBackground;
    public final @NotNull ColorPickerButton foregroundColorButton, backgroundColorButton;

    public SyntaxStyleOptionsView() {
        super(new GridBagLayout());

        // Foreground row
        final var fgLabel = new JLabel("Foreground");
        this.useForeground = new JCheckBox();
        this.foregroundColorButton = new ColorPickerButton(Color.BLACK);
        this.foregroundColorButton.setEnabled(this.useForeground.isSelected());
        this.useForeground.addChangeListener(e -> this.foregroundColorButton.setEnabled(this.useForeground.isSelected()));

        // Background row
        final var bgLabel = new JLabel("Background");
        this.useBackground = new JCheckBox();
        this.backgroundColorButton = new ColorPickerButton(Color.WHITE);
        this.backgroundColorButton.setEnabled(this.useBackground.isSelected());
        this.useBackground.addChangeListener(e -> this.backgroundColorButton.setEnabled(this.useBackground.isSelected()));

        // Bold row (no button)
        final var boldLabel = new JLabel("Bold");
        this.isBold = new JCheckBox();

        // Italic row (no button)
        final var italicLabel = new JLabel("Italic");
        this.isItalic = new JCheckBox();

        // Underline row (no button)
        final var underlineLabel = new JLabel("Underline");
        this.isUnderline = new JCheckBox();

        final var gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        // Establish a consistent row height across all grid rows (label/checkbox/button)
        final int baseLabelH = new JLabel("Label").getPreferredSize().height;
        final int baseCbH = new JCheckBox().getPreferredSize().height;
        final int baseBtnH = new JButton("Pick Color").getPreferredSize().height;
        final int rowHeight = Math.max(baseLabelH, Math.max(baseCbH, baseBtnH));

        addGridRow(this, gbc, 0, fgLabel, this.useForeground, this.foregroundColorButton, rowHeight);
        addGridRow(this, gbc, 1, bgLabel, this.useBackground, this.backgroundColorButton, rowHeight);
        addGridRow(this, gbc, 2, boldLabel, this.isBold, null, rowHeight);
        addGridRow(this, gbc, 3, italicLabel, this.isItalic, null, rowHeight);
        addGridRow(this, gbc, 4, underlineLabel, this.isUnderline, null, rowHeight);
    }

    private static void addGridRow(
        final @NotNull JPanel panel,
        final @NotNull GridBagConstraints gbc,
        final int row,
        final @NotNull JComponent label,
        final @NotNull JComponent checkbox,
        final @Nullable JComponent button,
        final int targetH
    ) {
        if (button != null) {
            final var buttonWidth = 100;
            final var size = new Dimension(buttonWidth, targetH);
            button.setPreferredSize(size);
            button.setMinimumSize(size);
            button.setMaximumSize(size);
        }

        final var labelSize = new Dimension(label.getPreferredSize().width, targetH);
        label.setPreferredSize(labelSize);
        label.setMinimumSize(new Dimension(0, targetH));
        checkbox.setPreferredSize(new Dimension(checkbox.getPreferredSize().width, targetH));
        checkbox.setMinimumSize(new Dimension(0, targetH));

        // add a filler to take remaining space to keep columns packed to the right
        gbc.gridx = 3;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(Box.createHorizontalStrut(0), gbc);
        // label in column 0
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(label, gbc);
        // checkbox in column 1
        gbc.gridx = 2;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(checkbox, gbc);
        // optional button in column 2
        if (button != null) {
            gbc.gridx = 3;
            gbc.gridy = row;
            gbc.weightx = 0;
            panel.add(button, gbc);
        }
    }
}
