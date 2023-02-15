// Maximum Subarray Sum with One Deletion
public class Solution_1186 {
    public int maximumSum(int[] arr) {
        int currentContSum = arr[0];
        int currentSum = arr[0];
        int maxSum = arr[0];

        for(int i = 1; i < arr.length; i++) {
            currentSum = Math.max(currentContSum, currentSum + arr[i]);
            currentSum = Math.max(currentSum, arr[i]);
            currentContSum = Math.max(currentContSum + arr[i], arr[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

}
