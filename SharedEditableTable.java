import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SharedEditableTable {
    private static SharedTableData sharedData;

    public static SharedTableData getSharedData() {
        return sharedData;
    }

    private static boolean isEditable = false; // Flag to identify editable window

    public static void setIsEditable(boolean isEditable) {
        SharedEditableTable.isEditable = isEditable;
    }

    static {
        try {
            sharedData = new SharedTableData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private JFrame frame;

    private JTable table;

    public JTable getTable() {
        return table;
    }

    public SharedEditableTable() {
        frame = new JFrame("Shared Editable Table");

        table = sharedData.getTableModel();

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        //frame.setVisible(true);
    }

    public static void setEditable(boolean flag) {
        isEditable = flag;
    }

    public static boolean isEditable() {
        return isEditable;
    }

    // Other forms can call this method to get the non-editable table
    public static JTable getNonEditableTable() {
        SharedTableData sharedData = getSharedData();
        JTable table = sharedData.getTableModel();
        table.setEnabled(false);
        return table;
    }

    public static JTable getEditableTable() {
        SharedTableData sharedData = getSharedData();
        JTable table = sharedData.getTableModel();
        table.setEnabled(true);
        return table;
    }

    // A separate class to encapsulate the shared table data
    static class SharedTableData {
        private DefaultTableModel model;

        public SharedTableData() throws SQLException {
            model = new CustomTableModel(); // Use the custom table model
            model.addColumn("Ticker Symbol");
            model.addColumn("Stock Name");
            model.addColumn("Price");

            // Database connection and table population
            Database.getInstance();
            Connection connection = Database.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM stocks");

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("Ticker"),
                        resultSet.getString("Name"),
                        resultSet.getDouble("Price"),
                });
            }
        }

        public JTable getTableModel() {
            return new JTable(model);
        }

        // Custom table model enforcing column data types and constraints
        static class CustomTableModel extends DefaultTableModel {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 1) {
                    return String.class; // First two columns must be strings
                } else if (columnIndex == 2) {
                    return Double.class; // Third column must be a positive number or positive decimal
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (column == 2 && aValue instanceof Number) {
                    double value = ((Number) aValue).doubleValue();
                    if (value >= 0) {
                        super.setValueAt(value, row, column);
                    }
                } else {
                    super.setValueAt(aValue, row, column);
                }
            }
        }
    }
    private static class NonEditableTable {
        private JFrame frame;

        public NonEditableTable() {
            frame = new JFrame("Non-Editable Table");

            JTable table = sharedData.getTableModel();
            table.setEnabled(false);

            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    }
}
