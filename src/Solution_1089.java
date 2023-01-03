class Solution_1089 {
    public void duplicateZeros(int[] arr) {
        int i = 0;
        int j = 0;
        //i is the new array index when duplicating zero, record j, when i exceeds array length
        while (i < arr.length) {
            if (arr[j] == 0) {
                i += 2;
            } else {
                i++;
            }
            j++;
        }
        j--;

        // the j stops at 0 and duplicate 0 exceeds total array, so omit one zero.
        if (i == arr.length) {
            arr[i - 1] = 0;
            i--;
            j--;
        }

        while (j >= 0) {
            if (arr[j] == 0) {
                arr[i] = 0;
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
    }
}