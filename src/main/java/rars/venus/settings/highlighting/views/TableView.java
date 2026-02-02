package rars.venus.settings.highlighting.views;

import org.jetbrains.annotations.NotNull;
import rars.venus.editors.TokenStyle;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static rars.util.Utils.deriveFontFromStyle;

public final class TableView extends JPanel {
    private static final int
        UNSELECTED_COLUMN_INDEX = 0,
        SELECTED_COLUMN_INDEX = 1;
    private final @NotNull JTable table;
    private final @NotNull Color baseForegroundColor, baseBackgroundColor;
    private final @NotNull Font baseFont;
    private @NotNull TokenStyle highlightedCellsStyle;

    public TableView(
        @NotNull Color baseForegroundColor,
        @NotNull Color baseBackgroundColor,
        @NotNull Font baseFont
    ) {
        super(new BorderLayout());
        this.highlightedCellsStyle = TokenStyle.DEFAULT;
        this.baseForegroundColor = baseForegroundColor;
        this.baseBackgroundColor = baseBackgroundColor;
        this.baseFont = baseFont;
        this.table = createTable();
        this.add(table, BorderLayout.CENTER);
    }

    private @NotNull JTable createTable() {
        final var table = new JTable(new HighlightingSettingsTableModel());
        final var columnModel = table.getColumnModel();
        final var unselectedColumnModel = columnModel.getColumn(UNSELECTED_COLUMN_INDEX);
        unselectedColumnModel.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
            ) {
                final var cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setFont(baseFont);
                cell.setForeground(baseForegroundColor);
                cell.setBackground(baseBackgroundColor);
                return cell;
            }
        });
        final var selectedColumnModel = columnModel.getColumn(SELECTED_COLUMN_INDEX);
        selectedColumnModel.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
            ) {
                final var cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setFont(deriveFontFromStyle(baseFont, highlightedCellsStyle));
                cell.setForeground(highlightedCellsStyle.foreground());
                cell.setBackground(highlightedCellsStyle.background());
                return cell;
            }
        });
        return table;
    }

    public void setHighlightedCellsStyle(
        @NotNull TokenStyle tokenStyle
    ) {
        this.highlightedCellsStyle = tokenStyle;
        this.table.repaint();
    }

    private static final class HighlightingSettingsTableModel extends AbstractTableModel {
        private final String[] columns = {"Unselected", "Selected"};

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return columns[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }
    }
}
