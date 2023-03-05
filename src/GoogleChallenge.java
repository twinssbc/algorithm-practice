import java.util.HashMap;

public class GoogleChallenge {
    //level 1
    public static int solution(int[] x, int[] y) {
        HashMap<Integer, Integer> maps = new HashMap<>();
        int[] bigger = x;
        int[] smaller = y;
        if (x.length < y.length) {
            bigger = y;
            smaller = x;
        }
        for (int n : bigger) {
            if (!maps.containsKey(n)) {
                maps.put(n, 1);
            } else {
                maps.put(n, maps.get(n) + 1);
            }
        }

        for (int n : smaller) {
            if (maps.containsKey(n)) {
                if (maps.get(n) == 1) {
                    maps.remove(n);
                } else {
                    maps.put(n, maps.get(n) - 1);
                }
            }
        }

        for (int n : maps.keySet()) {
            return n;
        }
        return -1;
    }

    public static void main(String[] args) {
        GoogleChallenge gc = new GoogleChallenge();
        System.out.println(gc.solution(new int[]{13, 5, 6, 2, 5}, new int[]{5, 2, 5, 13}));
        System.out.println(gc.solution(new int[]{14, 27, 1, 4, 2, 50, 3, 1}, new int[]{2, 4, -4, 3, 1, 1, 14, 27, 50}));
    }
}
