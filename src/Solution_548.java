import java.util.LinkedList;
import java.util.Queue;

public class Solution_548 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] provinces = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        int provinceNum = 0;
        for(int i = 0; i < n; i++) {
            if(provinces[i] == 0) {
                provinces[i] = ++provinceNum;
            }
            for(int j = i + 1; j < n; j++) {
                if(isConnected[i][j] == 1) {
                    queue.add(j);
                }
            }
            while(queue.size() > 0) {
                int index = queue.poll();
                if(provinces[index] == 0) {
                    provinces[index] = provinceNum;
                    for(int j = i + 1; j < n; j++) {
                        if(index != j && isConnected[index][j] == 1 && provinces[j] == 0) {
                            queue.add(j);
                        }
                    }
                }
            }
        }
        return provinceNum;
    }
}
