import java.util.*;

public class RandomGenerator {
    private int[] map;
    private int currentHighBound;
    private Random random;

    public RandomGenerator(int low, int high) {
        map = new int[high - low + 1];
        random = new Random();
        currentHighBound = map.length;
        for(int i = 0; i < map.length; i++) {
            map[i] = low + i;
        }
    }

    public int generate() {
        int index = random.nextInt(currentHighBound);
        int value = map[index];
        map[index] = map[currentHighBound-1];
        currentHighBound--;
        if(currentHighBound == 0) {
            currentHighBound = map.length;
        }
        return value;
    }

    public static void main(String[] args) {
        RandomGenerator rg = new RandomGenerator(1, 10);
        Set<Integer> appearedNumber = new HashSet<>();
        for(int i = 1; i <= 10; i++) {
            int number = rg.generate();
            if(appearedNumber.contains(number)) {
                System.out.println("Should not appear: " + number);
            } else {
                appearedNumber.add(number);
                System.out.println(number);
            }
        }
    }
}
