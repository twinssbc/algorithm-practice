public class Solution_912_MergeSort {
    public int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length-1);
        return nums;
    }

    public void mergeSort(int[] nums, int l, int r) {
        if(l < r) {
            int mid = (l+r)/2;
            mergeSort(nums, l, mid);
            mergeSort(nums, mid+1, r);
            merge(nums,l, r, mid);
        }
    }

    public void merge(int[] nums, int l, int r, int mid) {
        int[] auxArray = new int[r-l+1];
        int s1 = l;
        int s2 = mid + 1;
        int index = 0;
        while(s1 <= mid && s2 <= r) {
            if(nums[s1] > nums[s2]) {
                auxArray[index++] = nums[s2++];
            } else {
                auxArray[index++] = nums[s1++];
            }
        }

        while(s1 <= mid) {
            auxArray[index++] = nums[s1++];
        }

        while(s2 <= r) {
            auxArray[index++] = nums[s2++];
        }

        for(index = l; index <=r; index++) {
            nums[index] = auxArray[index-l];
        }
    }

}
