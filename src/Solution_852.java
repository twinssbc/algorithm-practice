public class Solution_852 {
    public int peakIndexInMountainArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while(start < end - 1) {
            int midIndex = (start + end) / 2;
            int left = arr[midIndex - 1];
            int mid = arr[midIndex];
            if(left < mid) {
                start = midIndex;
            } else {
                end = midIndex;
            }
        }
        if(start == end) {
            return start;
        } else if(arr[start] > arr[end]) {
            return start;
        } else {
            return end;
        }
    }
}
