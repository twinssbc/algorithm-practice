import java.util.ArrayDeque;
import java.util.Deque;

public class Solution_1696 {
    public int maxResult(int[] nums, int k) {
        Deque<Integer> indexQueue = new ArrayDeque<>();
        int[] scores = new int[nums.length];
        scores[0] = nums[0];
        indexQueue.offer(0);
        for(int i = 1; i < nums.length; i++) {
            scores[i] = scores[indexQueue.peekFirst()] + nums[i];

            while(!indexQueue.isEmpty()) {
                if(scores[indexQueue.peekLast()] <= scores[i]) {
                    indexQueue.pollLast();
                } else {
                    break;
                }
            }

            indexQueue.offerLast(i);
            if(indexQueue.peekFirst() <= (i - k)) {
                indexQueue.pollFirst();
            }
        }
        return scores[nums.length - 1];
    }

    public static void main(String[] args) {
        Solution_1696 solution = new Solution_1696();
        int[] nums1 = new int[] {1, -1, -2, 4, -7, 3};
        int k1 = 2;
        int answer1 = solution.maxResult(nums1, k1);
        assert answer1 == 7:"case 1";

        int[] nums2 = new int[] {10, -5, -2, 4, 0, 3};
        int k2 = 3;
        int answer2 = solution.maxResult(nums2, k2);
        assert answer2== 17:"case 2";

        int[] nums3 = new int[] {1,-5,-20,4,-1,3,-6,-3};
        int k3 = 2;
        int answer3 = solution.maxResult(nums3, k3);
        assert answer3 == 0:"case 3";

        int[] nums4 = new int[] {-1, -2, -5, -4, -3, -2};
        int k4 = 2;
        int answer4 = solution.maxResult(nums4, k4);
        assert answer4 == -9:"case 4";
    }
}
