import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NotificationsPanel extends JPanel implements NotificationListener {
    private JTextArea notificationsArea;

    public NotificationsPanel() {
        notificationsArea = new JTextArea(10, 30);
        notificationsArea.setEditable(false);
        add(notificationsArea);
    }

    public void addNotification(String notification) {
        notificationsArea.append(notification + "\n");
    }

    @Override
    public void onNotificationAdded(String notification) {
        addNotification(notification);
    }
}