import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Merge Intervals
// Time: O(nlogn), Space: O(logn)
public class Solution_56 {
    public int[][] merge(int[][] intervals) {
        List<int[]> results = new ArrayList<int[]>();
        TreeMap<Integer, Integer> positions = new TreeMap<Integer, Integer>();
        for(int[] interval:intervals) {
            if(!positions.containsKey(interval[0])) {
                positions.put(interval[0], 1);
            } else {
                positions.put(interval[0], positions.get(interval[0]) + 1);
            }
            if(!positions.containsKey(interval[1])) {
                positions.put(interval[1], -1);
            } else {
                positions.put(interval[1], positions.get(interval[1]) - 1);
            }
        }

        int count = 0;
        int start = 0;
        for(Map.Entry<Integer, Integer> keyValue: positions.entrySet()) {
            Integer value = keyValue.getValue();
            if(value == 0) {
                if(count == 0) {
                    results.add(new int[] {keyValue.getKey(), keyValue.getKey()});
                }
                continue;
            }
            int oriCount = count;
            count+=value;
            if(count > 0) {
                if(oriCount == 0) {
                    start = keyValue.getKey();
                }
            } else if(count == 0) {
                results.add(new int[] {start, keyValue.getKey()});
            }
        }

        return results.toArray(new int[0][0]);
    }

}
