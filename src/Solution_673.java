public class Solution_673 {
    public int findNumberOfLIS(int[] nums) {
        int[] longestLength = new int[nums.length];
        int[] longestCount = new int[nums.length];
        int maxLength = 1;
        longestLength[0] = 1;
        longestCount[0] = 1;
        for(int i = 1; i < nums.length; i++) {
            int longest = 0;
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j]) {
                    longest = Math.max(longest, longestLength[j]);
                }
            }

            if(longest > 0) {
                for(int j = 0; j < i; j++) {
                    if(longestLength[j] == longest && nums[i] > nums[j]) {
                        longestCount[i] += longestCount[j];
                    }
                }
            } else {
                longestCount[i] = 1;
            }
            longestLength[i] = longest + 1;
            maxLength = Math.max(longestLength[i], maxLength);
        }

        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            if(longestLength[i] == maxLength) {
                count+=longestCount[i];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution_673 solution = new Solution_673();
        int[] nums = {1,3,5,4,7};
        int answer = solution.findNumberOfLIS(nums);
        assert answer == 2: "case 1";

        int[] nums2 = {2,2,2,2,2};
        int answer2 = solution.findNumberOfLIS(nums2);
        assert answer2 == 5: "case 2";
    }
}
