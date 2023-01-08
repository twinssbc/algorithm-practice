public class Solution_845 {
    public int longestMountain(int[] arr) {
        if(arr.length < 3) {
            return 0;
        }
        int maxLength = 0;
        // 0: up, 1: down
        int flag = 0;
        int curLength = 1;

        for(int i = 1; i < arr.length; i++) {
            if(curLength + arr.length - i <= maxLength) {
                break;
            }
            if(flag == 0) {
                if(arr[i] > arr[i-1]) {
                    curLength++;
                } else if(arr[i] < arr[i-1]) {
                    if(curLength > 1) {
                        curLength++;
                        flag = 1;
                    } else {
                        curLength = 1;
                    }
                } else {
                    curLength = 1;
                }
            } else {
                if(arr[i] < arr[i-1]) {
                    curLength++;
                } else{
                    flag = 0;
                    if(curLength > maxLength) {
                        maxLength = curLength;
                    }
                    if(arr[i] > arr[i-1]) {
                        curLength = 2;
                    } else {
                        curLength = 1;
                    }
                }
            }
        }

        if(flag == 1 && curLength > maxLength) {
            maxLength = curLength;
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Solution_845 solution = new Solution_845();
        int[] arr1 = new int[]{2,1,4,7,3,2,5};
        int answer1 = solution.longestMountain(arr1);
        assert answer1 == 5: "case 1";

        int[] arr2 = new int[]{2,2,2};
        int answer2 = solution.longestMountain(arr2);
        assert answer2 == 0: "case 2";
    }
}
