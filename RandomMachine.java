/*
* randomMachine
* may be useful to modify the price of stocks
*  */


import java.util.*;

public class RandomMachine{

    private final static Random random = new Random();

    public static double get0to1() {
        return random.nextDouble();
    }

    // for example:
    // input: 4 -> output: 1234 or 12
    public static int generateRandomInt(int numberOfDigits) {
        if (numberOfDigits < 1 || numberOfDigits > 9) {
            throw new IllegalArgumentException("Number of digits must be between 1 and 9");
        }

        Random random = new Random();
        int min = (int) Math.pow(10, numberOfDigits - 1);
        int max = (int) Math.pow(10, numberOfDigits) - 1;

        return random.nextInt(max - min + 1) + min;
    }

    public static double randomizeValue(double oriValue) {
        double ratio = - 0.1 + get0to1() * 0.2;
        return (1 + ratio) * oriValue;
    }

    public static double getRandomDouble() {
        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random double between -0.5 and 0.5
        double randomValue = random.nextDouble() - 0.5;

        return randomValue;
    }

    // for example:
    // input: 4 -> output: 1234 or 7689
    // maybe id should be String
    public static String generateRandomNumberOfDigitsN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of digits must be positive");
        }

//        if (n > 10) {
//            throw new IllegalArgumentException("Too long number");
//        }

        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();

        // the first number is not 0
        randomNumber.append(random.nextInt(9) + 1);

        // the rest 0 - 9
        for (int i = 1; i < n; i++) {
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }

    public static int getRandomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static <T> T selectAnElement(ArrayList<T> list) {
        return selectRandomElements(list, 1).get(0);
    }

    public static <T> ArrayList<T> selectRandomElements(ArrayList<T> list, int n) {
        if (n <= 0 || list.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument for number of elements or empty list");
        }

        if (n > list.size()) {
            System.out.printf("The number n=%d is larger than the size of choices %d.\n", n, list.size());
            System.out.println("All selected");
            return new ArrayList<>(list); // Return a new list containing all elements
        }

        Set<Integer> pickedIndexes = new HashSet<>();
        ArrayList<T> randomElements = new ArrayList<>();

        while (randomElements.size() < n) {
            int randomIndex = random.nextInt(list.size());
            // Add the element to the result if it hasn't been added already
            if (pickedIndexes.add(randomIndex)) {
                randomElements.add(list.get(randomIndex));
            }
        }

        return randomElements;
    }

    public static int chooseInt(int size) {
        return random.nextInt(size);
    }

    public static <T> T RouletteSelect(HashMap<T, Double> prob) {
        double rand = RandomMachine.get0to1();
        T selection = null;

        double cumulativeProbability = 0.0;
        for (Map.Entry<T, Double> entry : prob.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (cumulativeProbability == 0)
                continue;
            selection = entry.getKey();
            if (rand < cumulativeProbability) {
                return selection;
            }
        }

        return selection;
    }

}
