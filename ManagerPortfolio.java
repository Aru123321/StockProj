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

public class ManagerPortfolio extends JFrame {
    private JLabel welcomeLabel;
    private JPanel panel1;
    private JTable Stocksj;
    private JPanel Info;
    private JScrollPane jScrollPane;
    private JTextField textField1;

    private JButton buyButton;
    private JButton sellButton;

    private String stockKey;
    private int sellOrBuyNumber;

    private void closeWithUpdate() {
        CurrentUsers.updateCurrentUsers(AccountManager.getInstance(), false);
        //System.out.println(CurrentUsers.getUsers());
        dispose(); // Close the current ManagerPortfolio window
    }

    public ManagerPortfolio(AccountManager accountManager) throws SQLException {

        setContentPane(panel1);
        setSize(700, 700);
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
        // Create a JScrollPane and set the table as its view
        SharedEditableTable sharedEditableTable = new SharedEditableTable();

        // Retrieve the non-editable table
        JTable nonEditableTable = SharedEditableTable.getNonEditableTable();

        JScrollPane scrollPane = new JScrollPane(nonEditableTable);
        panel1.add(scrollPane);

        add(panel1);
        setVisible(true);

    }
}
