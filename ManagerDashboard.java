import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;

public class ManagerDashboard extends JFrame {

    private void closeWithUpdate() {
        CurrentUsers.updateCurrentUsers(AccountManager.getInstance(), false);
        //System.out.println(CurrentUsers.getUsers());
        dispose(); // Close the current ManagerPortfolio window
    }


    public ManagerDashboard() throws SQLException {
        setTitle("Manager Dashboard");
        setSize(1300, 700);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(CurrentUsers.getUsers());

                closeWithUpdate(); // Call closeWithUpdate when window is closing
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set welcome message for the logged-in customer
        JOptionPane.showMessageDialog(null, "Welcome Account Manager!", "User authenticated", JOptionPane.INFORMATION_MESSAGE);

        JPanel panel = new JPanel();

        // Creating an instance of SharedEditableTable to access getNonEditableTable()
        SharedEditableTable sharedEditableTable = new SharedEditableTable();

        // Retrieve the non-editable table
        JTable EditableTable = SharedEditableTable.getEditableTable();

        JScrollPane scrollPane = new JScrollPane(EditableTable);
        panel.add(scrollPane);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) EditableTable.getModel();
                int rowCount = model.getRowCount();

                for (int i = 0; i < rowCount; i++) {
                    String ticker = model.getValueAt(i, 0).toString();
                    String name = model.getValueAt(i, 1).toString();
                    double price = Double.parseDouble(model.getValueAt(i, 2).toString());

                    // Assuming you have a method updateRecordInDatabase
                    // that updates the record in your database
                    // Replace this with your database update logic
                    Queries.updateStockByTicker(ticker, name, price);
                }
                JOptionPane.showMessageDialog(null, "Stocks Updated!");
            }
        });

        panel.add(updateButton, BorderLayout.SOUTH);
        String[] columns = {"ID", "Name", "Username", "Password", "UnProfits", "Profits", "PAccountID", "Options", "Balance"};

        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        ResultSet resultSet = Queries.fetchAllUsers();

        populateTable( model, resultSet);
        JTable table = new JTable(model);


        JScrollPane scrollPaneUsers = new JScrollPane(table);
        scrollPaneUsers.setPreferredSize(new Dimension(800, 300));
        panel.add(scrollPaneUsers);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(model);
            }
        });
        panel.add(refreshButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
        scheduleUpdateTask(updateButton);
    }

    // Method to populate the table from a ResultSet
    private void populateTable(DefaultTableModel model, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Object[] rowData = {
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Username"),
                    resultSet.getString("Password"),
                    resultSet.getDouble("UnProfits"),
                    resultSet.getDouble("Profits"),
                    resultSet.getInt("PAccountID"),
                    resultSet.getString("Options"),
                    resultSet.getDouble("Balance")
            };
            model.addRow(rowData);
        }
    }

    private void refreshTable(DefaultTableModel model) {
        try {
            ResultSet resultSet = Queries.fetchAllUsers();

            model.setRowCount(0); // Clear the table
            populateTable(model, resultSet); // Populate the table with the updated ResultSet

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void scheduleUpdateTask(JButton updateButton) {

        int delay = 60 * 1000; // 20 seconds in milliseconds
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DefaultTableModel tableModel = (DefaultTableModel) SharedEditableTable.getEditableTable().getModel();
                int rowCount = tableModel.getRowCount();

                // Calculate the new value by adding or subtracting a percentage from the current value
                for (int i = 0; i < rowCount; i++) {
                    double currentValue = (double) tableModel.getValueAt(i, 2); // Third column (index 2)
                    double percentageChange = RandomMachine.getRandomDouble(); // Change this percentage as needed

                    // Assuming the value is to be increased by 5% each time
                    double newValue = currentValue * (1 + percentageChange);

                    // Update the value in the table model
                    tableModel.setValueAt(newValue, i, 2);
                }
                updateButton.doClick();

            }
        }, delay, delay);
    }
}