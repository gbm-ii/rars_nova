package rars.venus.settings.editor.controllers;

import org.jetbrains.annotations.NotNull;
import rars.settings.TokenSettingKey;
import rars.venus.editors.TextEditingArea;
import rars.venus.editors.TokenStyle;
import rars.venus.settings.editor.views.SyntaxStylesView;

import javax.swing.event.ChangeListener;
import java.util.List;

public final class SyntaxStyleSettingsController {
    private final @NotNull SyntaxStylesView view;
    private final @NotNull EditorSettingsController parentController;
    private final @NotNull TextEditingArea textArea;
    private @NotNull TokenSettingKey currentKey;

    public SyntaxStyleSettingsController(
        final @NotNull SyntaxStylesView view,
        final @NotNull EditorSettingsController parentController,
        final @NotNull TextEditingArea textArea
    ) {
        this.view = view;
        this.parentController = parentController;
        this.textArea = textArea;
        this.currentKey = TokenSettingKey.COMMENT; // initial value
        initializeView();

        // initialize selection based on the list
        final var selected = this.view.tokenTypesList.getSelectedValue();
        if (selected != null) {
            setCurrentKey(selected);
        } else {
            setCurrentKey(this.currentKey);
        }
    }

    private void initializeView() {
        final ChangeListener tokenStyleChangeListener = e -> {
            final var tokenStyle = getTokenStyleFromView();
            this.parentController.settingsTheme.tokenStyles.put(this.currentKey, tokenStyle);
            TokenSettingKey
                .getTokenTypesForSetting(this.currentKey)
                .forEach(key -> this.textArea
                    .setTokenStyle(key, tokenStyle)
                );
        };
        final var syntaxOptions = view.syntaxStyleOptionsView;
        List.of(
            syntaxOptions.useForeground,
            syntaxOptions.foregroundColorButton,
            syntaxOptions.useBackground,
            syntaxOptions.backgroundColorButton,
            syntaxOptions.isBold,
            syntaxOptions.isItalic,
            syntaxOptions.isUnderline
        ).forEach(component -> component.addChangeListener(tokenStyleChangeListener));

        // selection listener for the token types list
        view.tokenTypesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                final var key = view.tokenTypesList.getSelectedValue();
                if (key != null) {
                    setCurrentKey(key);
                }
            }
        });
    }

    private @NotNull TokenStyle getTokenStyleFromView() {
        final var syntaxOptions = view.syntaxStyleOptionsView;
        final var foreground = syntaxOptions.useForeground.isSelected()
            ? syntaxOptions.foregroundColorButton.getColor()
            : null;
        final var background = syntaxOptions.useBackground.isSelected()
            ? syntaxOptions.backgroundColorButton.getColor()
            : null;
        final var isBold = syntaxOptions.isBold.isSelected();
        final var isItalic = syntaxOptions.isItalic.isSelected();
        final var isUnderline = syntaxOptions.isUnderline.isSelected();
        return new TokenStyle(foreground, background, isBold, isItalic, isUnderline);
    }

    public void setCurrentKey(final @NotNull TokenSettingKey key) {
        this.currentKey = key;
        this.setView(key);
    }

    public void resetButtonValues() {
        this.setView(this.currentKey);
    }

    private void setView(final @NotNull TokenSettingKey key) {
        final var settingsTheme = this.parentController.settingsTheme;
        final var tokenStyle = settingsTheme.tokenStyles.get(key);
        final var syntaxOptions = view.syntaxStyleOptionsView;
        final var foreground = tokenStyle.foreground();
        if (foreground != null) {
            syntaxOptions.useForeground.setSelected(true);
            syntaxOptions.foregroundColorButton.setColor(foreground);
        } else {
            syntaxOptions.useForeground.setSelected(false);
            syntaxOptions.foregroundColorButton.setColor(settingsTheme.foregroundColor);
        }
        final var background = tokenStyle.background();
        if (background != null) {
            syntaxOptions.useBackground.setSelected(true);
            syntaxOptions.backgroundColorButton.setColor(background);
        } else {
            syntaxOptions.useBackground.setSelected(false);
            syntaxOptions.backgroundColorButton.setColor(settingsTheme.backgroundColor);
        }
        syntaxOptions.isBold.setSelected(tokenStyle.isBold());
        syntaxOptions.isItalic.setSelected(tokenStyle.isItalic());
        syntaxOptions.isUnderline.setSelected(tokenStyle.isUnderline());
    }
}
