public class CheckAndReason {

    boolean success;
    String reason;

    CheckAndReason(boolean success) {
        this.success = success;
        this.reason = "";
    }

    CheckAndReason(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
