import java.util.Arrays;

class Solution_1089 {
    public void duplicateZeros(int[] arr) {
        int i = -1;
        int j = -1;
        //i is the new array index when duplicating zero, record j, when i exceeds array length
        while (i < arr.length - 1) {
            j++;
            if (arr[j] == 0) {
                i += 2;
            } else {
                i++;
            }
        }

        while (j >= 0) {
            if (arr[j] == 0) {
                //it's possible, i exceeds the array length
                if(i < arr.length) {
                    arr[i] = 0;
                }
                arr[i - 1] = 0;
                i -= 2;
            } else {
                arr[i] = arr[j];
                i--;
            }
            j--;
        }
    }

    public static void main(String[] args) {
        Solution_1089 solution = new Solution_1089();
        int[] arr0 = new int[] {1,0,0,0,0,1};
        solution.duplicateZeros(arr0);
        System.out.println(Arrays.toString(arr0));

        int[] arr1 = new int[] {1,0,2,3,0,4,5,0};
        solution.duplicateZeros(arr1);
        System.out.println(Arrays.toString(arr1));

        int[] arr2 = new int[] {1,2,3};
        solution.duplicateZeros(arr2);
        System.out.println(Arrays.toString(arr2));
    }
}