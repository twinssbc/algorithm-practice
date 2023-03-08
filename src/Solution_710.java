import java.util.*;

//Random Pick with Blacklist
// Time: O(B), Space: O(B)
public class Solution_710 {
    private Map<Integer, Integer> remap = new HashMap<>();
    private Random random;
    private int whitelistNumber;
    public Solution_710(int n, int[] blacklist) {
        Set<Integer> whiteList = new HashSet<>();
        whitelistNumber = n - blacklist.length;
        random = new Random();
        for(int i = whitelistNumber; i < n; i++) {
            whiteList.add(i);
        }
        for(int i = 0; i < blacklist.length; i++) {
            whiteList.remove(blacklist[i]);
        }

        Iterator<Integer> itr = whiteList.iterator();
        for(int i = 0; i < blacklist.length; i++) {
            if(blacklist[i] < whitelistNumber) {
                remap.put(blacklist[i], itr.next());
            }
        }
    }

    public int pick() {
        int rand = random.nextInt(whitelistNumber);
        if(remap.containsKey(rand)) {
            return remap.get(rand);
        }
        return rand;
    }
}
