import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormManager extends JFrame{
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton SuButton;

    public LoginFormManager() {
        setContentPane(LoginPanel);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

        SuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());

                if (Controller.managerCheck(username, password) && !(CurrentUsers.checkUser(AccountManager.getInstance()))) {
                    tfUsername.setText("");
                    pfPassword.setText("");
                    CurrentUsers.updateCurrentUsers(AccountManager.getInstance(),true);
                    JOptionPane.showMessageDialog(null, "User is authenticated", "User authenticated", JOptionPane.INFORMATION_MESSAGE);
                    ManagerDashboard dashboard = null;
                    try {
                        dashboard = new ManagerDashboard();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //setVisible(false); // Hide the login/signup page
                    dashboard.setVisible(true);
                    dispose();
                }
                else if (CurrentUsers.checkUser(AccountManager.getInstance())){
                    JOptionPane.showMessageDialog(null, "Manager is already logged in", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null, "Credentials are wrong", "User not authenticated", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
