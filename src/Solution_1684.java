import java.util.HashSet;
import java.util.Set;

public class Solution_1684 {
    public int countConsistentStrings(String allowed, String[] words) {
        int consistentCount = 0;
        Set<Character> set = new HashSet<>();
        for(int i = 0; i < allowed.length(); i++) {
            set.add(allowed.charAt(i));
        }

        for(String word:words) {
            boolean notMatch = false;
            for(int j = 0; j < word.length(); j++) {
                if(!set.contains(word.charAt(j))) {
                    notMatch = true;
                    break;
                }
            }
            if(!notMatch) {
                consistentCount ++;
            }
        }

        return consistentCount;
    }
}
