import java.util.*;

public class Solution_2503 {
    public class Coordinate {
        public int row;
        public int col;
        public int value;

        public Coordinate(int r, int c, int v) {
            this.row = r;
            this.col = c;
            this.value = v;
        }
    }

    public int[] maxPoints(int[][] grid, int[] queries) {
        int rowCount = grid.length;
        int colCount = grid[0].length;
        int[] answers = new int[queries.length];

        // query value, original query position
        TreeMap<Integer, List<Integer>> sortedQueries = new TreeMap<>();
        for (int i = 0; i < queries.length; i++) {
            if (sortedQueries.containsKey(queries[i])) {
                sortedQueries.get(queries[i]).add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                sortedQueries.put(queries[i], list);
            }
        }

        // BFS
        Queue<Coordinate> queue = new LinkedList<Coordinate>();
        Queue<Coordinate> leafQueue = new LinkedList<Coordinate>();
        boolean[][] added = new boolean[rowCount][colCount];
        int points = 0;

        queue.add(new Coordinate(0, 0, grid[0][0]));
        added[0][0] = true;

        int[][] delta = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (Map.Entry<Integer, List<Integer>> entry : sortedQueries.entrySet()) {
            Integer queryNumber = entry.getKey();
            while (!queue.isEmpty()) {
                Coordinate coordinate = queue.poll();
                if (coordinate.value < queryNumber) {
                    points += 1;

                    for (int i = 0; i < delta.length; i++) {
                        int adjR = coordinate.row + delta[i][0];
                        if (adjR < 0 || adjR >= rowCount) {
                            continue;
                        }
                        int adjC = coordinate.col + delta[i][1];
                        if (adjC < 0 || adjC >= colCount) {
                            continue;
                        }
                        if (added[adjR][adjC]) {
                            continue;
                        } else {
                            queue.add(new Coordinate(adjR, adjC, grid[adjR][adjC]));
                            added[adjR][adjC] = true;
                        }
                    }
                } else {
                    leafQueue.add(coordinate);
                }
            }
            List<Integer> answerPosition = entry.getValue();
            for (Integer position : answerPosition) {
                answers[position] = points;
            }

            queue = leafQueue;
            leafQueue = new LinkedList<>();
        }

        return answers;
    }

    public static void main(String[] args) {
        Solution_2503 solution = new Solution_2503();

        int[][] grid = {{1, 2, 3}, {2, 5, 7}, {3, 5, 1}};
        int[] queries = {5, 6, 2};
        int[] answers = solution.maxPoints(grid, queries);
        System.out.println(Arrays.toString(answers));

        int[][] grid2 = {{5, 2, 1}, {1, 1, 2}};
        int[] queries2 = {3};
        int[] answers2 = solution.maxPoints(grid2, queries2);
        System.out.println(Arrays.toString(answers2));


    }
}
