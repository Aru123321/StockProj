import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignupCust extends JFrame {
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton SuButton;
    private JButton backToLoginButton;
    private JTextField balance;
    private JTextField pID;
    private JTextField Name;

    public SignupCust(){
        setContentPane(LoginPanel);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
        SuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());
                String name = Name.getText();
                String pAID = (pID.getText());
                String Balance = (balance.getText());

                if ( (username.isEmpty()) || (password.isEmpty()) || (name.isEmpty()) || (pAID.isEmpty()) || (Balance.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Please leave no field blank", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }
                else if (!Controller.isPositiveNumber(pAID) || !Controller.isPositiveNumber(Balance)) {
                    JOptionPane.showMessageDialog(null, "Personal Account ID and Balance must be positive numbers", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }
                else if ( Integer.parseInt(Balance) < 1000) {
                    JOptionPane.showMessageDialog(null, "Personal Account must have balance >= $1000", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        if (Queries.fetchUserByUsername(username).next()) {
                            JOptionPane.showMessageDialog(null, "This user already exists", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Queries.addUser(name, username, password, 0, 0, Integer.parseInt(pAID), "FALSE", Integer.parseInt(Balance));
                            JOptionPane.showMessageDialog(null, "Congratulations! You have made an account. You may exit this page and login.", "User authenticated", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        });
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
