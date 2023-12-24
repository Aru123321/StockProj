// Abstract User class capturing common attributes and methods for different types of users
public abstract class User {
    private int userID;
    private String username;
    private String password;

    public abstract String toString();

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public abstract boolean equals(User user);
}
