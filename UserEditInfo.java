import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserEditInfo extends JFrame{
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JButton SuButton;
    private JButton backToLoginButton;
    private JTextField balance;
    private JTextField Name;
    private JTextField password;
    private JLabel personalBalanceLive;
    private JLabel derivTrading;
    private JLabel unrealizedField;

    private double balanceTracker;


    private void closeWithUpdate(CustomersDashboard cDashboard) {
        dispose(); // Close the current Dashboard window
        cDashboard.setVisible(true);
    }

    public UserEditInfo(CustomersDashboard cDashboard){
        setContentPane(LoginPanel);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(CurrentUsers.getUsers());

                closeWithUpdate(cDashboard); // Call closeWithUpdate when window is closing
            }
        });

        System.out.println(cDashboard.getCustomer().getUnProfits());
        System.out.println(cDashboard.getCustomer().getProfits());

/*        unRealized.setText(Double.toString(cDashboard.getCustomer().getUnProfits()));
        realized.setText(Double.toString(cDashboard.getCustomer().getProfits()));

        unRealized.setText("THIS WORKS");
        realized.setText("THIS WORKS");*/

        unrealizedField.setText("Unrealized Profit: $" + cDashboard.getCustomer().getUnProfits() + " Realized Profit: $" + cDashboard.getCustomer().getProfits());

        balanceTracker = cDashboard.getCustomer().getBalance();

        Name.setText(cDashboard.getCustomer().getName());
        String personalBalanceLiveStarter = personalBalanceLive.getText();
        personalBalanceLive.setText(personalBalanceLiveStarter + balanceTracker + ")");
        derivTrading.setText(derivTrading.getText() + (cDashboard.getCustomer().getOptions().equalsIgnoreCase("true") ? "YES" : "NO"));
        password.setText(cDashboard.getCustomer().getPassword());

        setVisible(true);
        SuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String passwordOld = password.getText();
                String nameOld = Name.getText();
                String balanceOld = (balance.getText());

                String passwordNew = password.getText();
                String name = Name.getText();
                String Balance = (balance.getText());

                if ( (name.isEmpty()) || (passwordNew.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Name and Password must not be blank", "Changes not saved", JOptionPane.ERROR_MESSAGE);
                }
                else if (!Controller.isPositiveNumber(Balance) && !Balance.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Personal Balance addition must be positive number", "Changes not saved", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (!Balance.isEmpty()){
                        balanceTracker += Double.parseDouble(Balance);
                    }

                    System.out.println("Balance updated");
                    personalBalanceLive.setText(personalBalanceLiveStarter + balanceTracker + ")");
                    Queries.updateUserDetails(cDashboard.getCustomer().getID(), name, cDashboard.getCustomer().getUsername() ,passwordNew, cDashboard.getCustomer().getUnProfits(), cDashboard.getCustomer().getProfits(), cDashboard.getCustomer().getPAccountID(), String.valueOf(cDashboard.getCustomer().getOptions()), balanceTracker);
                    cDashboard.getCustomer().updateCustomer( name ,passwordNew, cDashboard.getCustomer().getUnProfits(), cDashboard.getCustomer().getProfits(), cDashboard.getCustomer().getPAccountID(), String.valueOf(cDashboard.getCustomer().getOptions()), balanceTracker);
                    Name.setText(name);
                    derivTrading.setText(derivTrading.getText() + (Boolean.parseBoolean(cDashboard.getCustomer().getOptions()) ? "YES" : "NO"));
                    password.setText(cDashboard.getCustomer().getPassword());

                    JOptionPane.showMessageDialog(null, "Congratulations! Your changes have been reflected.", "Changes saved", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                cDashboard.setVisible(true);
            }
        });
    }
}
