public class Solution_1326 {
    public int minTaps(int n, int[] ranges) {
        int[] rangeEnd = new int[n+1];
        // overlap the range, store the farthest end for the same start index
        for(int i = 0; i < n + 1; i++) {
            if(ranges[i] == 0) continue;
            int start = Math.max(0, i - ranges[i]);
            rangeEnd[start] = Math.max(rangeEnd[start], i + ranges[i]);
        }

        int count = 0;
        int start = 0;
        int end = 0;
        int currentEnd = 0;
        while(start < n + 1 && end < n) {
            count++;
            int nextEnd = end;
            while(start < n + 1 && start <= end) {
                nextEnd = Math.max(nextEnd, rangeEnd[start++]);
            }
            // can't move end further
            if(end == nextEnd) {
                return -1;
            } else {
                end = nextEnd;
            }
        }

        return count;
    }
}
