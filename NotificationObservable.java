import java.util.ArrayList;
import java.util.List;

public class NotificationObservable {
    private List<NotificationListener> listeners = new ArrayList<>();

    public void addListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void removeListener(NotificationListener listener) {
        listeners.remove(listener);
    }

    public void addNotification(String notification) {
        for (NotificationListener listener : listeners) {
            listener.onNotificationAdded(notification);
        }
    }
}