import java.util.HashSet;

public class IDGenerator {

    private static final int IDDigits = 8;
    private static final HashSet<String> ids = new HashSet<>();

    public static String generateID() {
        String id;
        while (true) {
           id = RandomMachine.generateRandomNumberOfDigitsN(IDDigits);
           if (!ids.contains(id)) {
               ids.add(id);
               return id;
           }
        }
    }
}
