import java.util.LinkedList;
import java.util.Queue;


// Number of Islands
public class Solution_200 {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int islandCount = 0;

        int[][] delta = new int[][] {{1, 0}, { -1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '1' && !visited[i][j]) {
                    islandCount++;
                    queue.offer(new int[] { i, j});
                    visited[i][j] = true;
                    while(!queue.isEmpty()) {
                        int[] coordinate = queue.poll();
                        for(int[] dt: delta) {
                            int newi = coordinate[0] + dt[0];
                            int newj = coordinate[1] + dt[1];
                            if(newi >= 0 && newj >=0 && newi < m && newj < n) {
                                if(grid[newi][newj] == '1' && !visited[newi][newj]) {
                                    queue.offer(new int[] {newi, newj});
                                    visited[newi][newj] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return islandCount;
    }

}
