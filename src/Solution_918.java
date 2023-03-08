// Maximum Sum Circular Subarray
// TIme: O(N), Space: O(1)
public class Solution_918 {
    public int maxSubarraySumCircular(int[] nums) {
        int maxSum = nums[0];
        int currentMaxSum = 0;
        int minSum = nums[0];
        int currentMinSum = 0;
        int totalSum = 0;

        //calculate the max sub array as normal sum
        //calculate the min sub array for special sum, which contains the rotated part
        for(int num: nums) {
            currentMaxSum = Math.max(currentMaxSum + num, num);
            maxSum = Math.max(maxSum, currentMaxSum);
            currentMinSum = Math.min(currentMinSum + num, num);
            minSum = Math.min(minSum, currentMinSum);
            totalSum += num;
        }
        return totalSum == minSum ? maxSum: Math.max(maxSum, totalSum-minSum);
    }
}
