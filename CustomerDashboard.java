
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDashboard extends JFrame {
    private JLabel welcomeLabel;
    private JPanel panel1;
    private JPanel Info;
    private JTextField textField1;
    private JPanel panelTransaction;
    private JLabel UserInfo;
    private JCheckBox eligibleForDerivativeTradingCheckBox;
    private JPanel panelStocks;

    private JButton buyButton;
    private JButton sellButton;

    private JButton accountInfo;

    private String stockKey;
    private int sellOrBuyNumber;

    private Customer customer;

    private void closeWithUpdate() {
        CurrentUsers.updateCurrentUsers(customer, false);
        System.out.println(CurrentUsers.getUsers());
        dispose(); // Close the current Dashboard window
    }

    public Customer getCustomer(){
        return customer;
    }


    public CustomerDashboard(Customer customer) throws SQLException {
        this.customer = customer;
        add(panelStocks);

        System.out.println(customer);
        UserInfo.setText(customer.getUsername() + "'s Dashboard");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(CurrentUsers.getUsers());

                closeWithUpdate(); // Call closeWithUpdate when window is closing
            }
        });

        setContentPane(panel1);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set welcome message for the logged-in customer
        JOptionPane.showMessageDialog(null, "Welcome " + customer.getUsername() + "!", "User authenticated", JOptionPane.INFORMATION_MESSAGE);

        SharedEditableTable sharedEditableTable = new SharedEditableTable();

        // Retrieve the non-editable table
        JTable nonEditableTable = SharedEditableTable.getNonEditableTable();

        JScrollPane scrollPane = new JScrollPane(nonEditableTable);
        panelStocks.add(scrollPane);
        setVisible(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));


        accountInfo = new JButton("View and Edit Account Info");
/*        accountInfo.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  setVisible(false);
                  new UserEditInfo(CustomerDashboard.this);
              }
        });*/

        buyButton = new JButton("Buy Stocks");
        /*buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    new StockPurchase(CustomerDashboard.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });*/

        sellButton = new JButton("View and Sell Your Stocks");

    /*    sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    new StockSale(CustomerDashboard.this);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });*/

        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(accountInfo);

        // Add the button panel to the main panel (panel1)
        panelTransaction.setLayout(new BorderLayout());
        panelTransaction.add(buttonPanel, BorderLayout.SOUTH);

        panelTransaction.add(buyButton);
        panelTransaction.add(sellButton);

        setVisible(true);
    }
}
