import Database.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Logincust extends JFrame{
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton lgButton;
    private JButton SuButton;


    public Logincust(){

        setContentPane(LoginPanel);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
        lgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());

                //System.out.println(Queries.fetchStockByTicker("AAPL"));



                if (CurrentUsers.checkUsername(username)){
                    JOptionPane.showMessageDialog(null, "User is already logged in", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }

                else if (Controller.checkCredentials(username, password)) {
                    tfUsername.setText("");
                    pfPassword.setText("");
                    JOptionPane.showMessageDialog(null, "User is authenticated", "User authenticated", JOptionPane.INFORMATION_MESSAGE);
                    Customer customer = null;
                    try {
                        customer = new Customer(username, password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    CurrentUsers.updateCurrentUsers(customer, true);
                    System.out.println(CurrentUsers.getUsers());
                    CustomersDashboard dashboard = null;
                    dashboard = new CustomersDashboard(customer);
                    //setVisible(false); // Hide the login/signup page
                    dashboard.setVisible(true);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Credentials are wrong", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupCust signupCust = new SignupCust();
            }
        });
    }

}

