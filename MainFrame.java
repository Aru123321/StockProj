import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainFrame extends JFrame implements NotificationListener {
    private static NotificationObservable observable;
    private NotificationsPanel notificationsPanel;

    public MainFrame(int i) {
        if (observable == null) {
            observable = new NotificationObservable(); // In case it's not initialized
            observable.addListener(this);
        } else {
            observable.addListener(this); // All frames listen to the same observable
        }
        notificationsPanel = new NotificationsPanel();

        add(notificationsPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        addNotification(String.valueOf(i));
        JButton updateButton = new JButton("Generate Random Int");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                int randomInt = rand.nextInt(100); // Change this range as needed
                addNotification("Random Int: " + randomInt);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH); // Consider the layout you want (e.g., BorderLayout.SOUTH)
        pack(); // Ensure the components are laid out properly
    }

    public void addNotification(String notification) {
        observable.addNotification(notification);
    }

    @Override
    public void onNotificationAdded(String notification) {
        notificationsPanel.addNotification(notification);
    }


}