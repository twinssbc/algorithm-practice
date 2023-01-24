import java.util.*;

public class Solution_1494 {
    int k;
    int n;
    int[] prerequisites;
    Map<Integer, Integer> memo;

    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        this.k = k;
        this.n = n;
        this.prerequisites = new int[n];
        this.memo = new HashMap<>();
        int takenBitmask = 0;

        for (int i = 0; i < n; i++) {
            takenBitmask |= (1 << i);
        }

        for (int[] relation : relations) {
            this.prerequisites[relation[1] - 1] |= (1 << relation[0] - 1);
        }

        return backtrack(takenBitmask);
    }

    public int backtrack(int takenBitmask) {
        //all courses taken
        if (takenBitmask == 0) {
            return 0;
        } else if (memo.containsKey(takenBitmask)) {
            return memo.get(takenBitmask);
        } else {
            List<Integer> availableCourse = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((takenBitmask & (1 << i)) != 0 && (prerequisites[i] & takenBitmask) == 0) {
                    availableCourse.add(i);
                }
            }

            // if available course is less than k, then take all of them
            if (availableCourse.size() <= k) {
                int temp = takenBitmask;
                for (Integer takenCourse : availableCourse) {
                    temp = temp ^ (1 << takenCourse);
                }
                memo.put(takenBitmask, 1 + backtrack(temp));
            } else {
                int answer = Integer.MAX_VALUE;
                List<Integer> combinationsInK = new ArrayList<>();
                // take k courses
                getAllCombination(combinationsInK, availableCourse, k, takenBitmask, 0);
                for (Integer combination : combinationsInK) {
                    answer = Math.min(answer, 1 + backtrack(combination));
                }
                memo.put(takenBitmask, answer);
            }
        }

        return memo.get(takenBitmask);
    }

    public void getAllCombination(List<Integer> combinationsInK, List<Integer> availableCourse, int k, int currentMask, int currentIndex) {
        if (k == 0) {
            combinationsInK.add(currentMask);
        } else {
            for (int i = currentIndex; i < availableCourse.size(); i++) {
                int temp = currentMask ^ (1 << availableCourse.get(i));
                getAllCombination(combinationsInK, availableCourse, k - 1, temp, i + 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution_1494 solution = new Solution_1494();
//        int n = 4;
//        int[][] relations = {{2,1},{3,1},{1,4}};
//        int k = 2;

        int n = 5;
        int[][] relations = {{2,1},{3,1},{4,1}, {1,5}};
        int k = 2;

//        int n = 2;
//        int[][] relations = {};
//        int k = 2;
        System.out.println(solution.minNumberOfSemesters(n, relations, k));
    }
}
