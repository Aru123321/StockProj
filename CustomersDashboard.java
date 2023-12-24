import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class CustomersDashboard extends JFrame {

    private Customer customer;
    public Customer getCustomer(){
        return customer;
    }

    private void closeWithUpdate() {
        CurrentUsers.updateCurrentUsers(customer, false);
        dispose();
    }

    public CustomersDashboard(Customer customer) {
        this.customer = customer;
        setTitle("Welcome " + customer.getUsername() + "!");
        setSize(700, 700);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(CurrentUsers.getUsers());

                closeWithUpdate(); // Call closeWithUpdate when window is closing
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JOptionPane.showMessageDialog(null, "Welcome " + customer.getUsername() + "!", "User authenticated", JOptionPane.INFORMATION_MESSAGE);

        // Set welcome message for the logged-in customer
        JPanel panel = new JPanel();

        // Creating an instance of SharedEditableTable to access getNonEditableTable()
        SharedEditableTable sharedEditableTable = new SharedEditableTable();

        SharedEditableTable.setEditable(false);

        // Retrieve the non-editable table
        JTable EditableTable = SharedEditableTable.getEditableTable();

        EditableTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (row != TableModelEvent.HEADER_ROW) {
                TableModel tableModel = (TableModel) e.getSource();

                String ticker = tableModel.getValueAt(row, 0).toString();
                Double newStockPrice = Double.parseDouble(tableModel.getValueAt(row, 2).toString());

                this.customer.updateUnrealizedGains(ticker, newStockPrice);

            }
        });

        EditableTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(EditableTable);
        panel.add(scrollPane);

        add(panel);

        JPanel buttonPanel = new JPanel();
        JButton accountInfo = new JButton("View and Edit Account Info");
        accountInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new UserEditInfo(CustomersDashboard.this);
            }
        });

        JButton buyButton = new JButton("Buy Stocks");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    new StockPurchase(CustomersDashboard.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton sellButton = new JButton("View and Sell Your Stocks");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    new StockSale(CustomersDashboard.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(accountInfo);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
        setVisible(true);
    }
}