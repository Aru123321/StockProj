import java.util.ArrayList;

public class CurrentUsers {
    private static CurrentUsers instance;
    private static ArrayList<User> users;

    private CurrentUsers() {
        users = new ArrayList<>();
    }

    public static synchronized CurrentUsers getInstance() {
        if (instance == null) {
            instance = new CurrentUsers();
        }
        return instance;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static boolean checkUser(User user){

      //  System.out.println(user);
        System.out.println(users.contains(user) + " THIS IS THE CHECK");
        return users.contains(user);
    }

    public static boolean checkUsername(String username){
        System.out.println(users);
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }



    public static void updateCurrentUsers(User user, boolean add){
        if (add){
            users.add(user);
        }
        else{
            users.remove(user);
        }
    }

}
