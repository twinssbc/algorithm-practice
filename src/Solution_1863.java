import java.util.ArrayList;
import java.util.List;

public class Solution_1863 {
    public int subsetXORSum(int[] nums) {
        List<Integer> results = new ArrayList<Integer>();
        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            int stopIndex = results.size();
            int num = nums[i];
            results.add(num);
            count += num;
            for(int j = 0; j < stopIndex; j++) {
                int result = num^results.get(j);
                results.add(result);
                count += result;
            }
        }
        return count;
    }
}
