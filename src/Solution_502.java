import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution_502 {
    class Project implements Comparable<Project> {
        int profit;
        int capital;
        Project(int profit, int capital) {
            this.profit = profit;
            this.capital = capital;
        }

        public int compareTo(Project other) {
            return this.capital - other.capital;
        }
    }
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        Project[] projects = new Project[profits.length];
        for(int i = 0; i < profits.length; i++) {
            projects[i] = new Project(profits[i], capital[i]);
        }
        Arrays.sort(projects);

        PriorityQueue<Project> queue = new PriorityQueue<>((p1, p2)-> p2.profit - p1.profit);
        int unAvailableProjectIndex = 0;
        while(k > 0) {
            for(; unAvailableProjectIndex < profits.length; unAvailableProjectIndex++) {
                if(w >= projects[unAvailableProjectIndex].capital) {
                    queue.add(projects[unAvailableProjectIndex]);
                } else {
                    break;
                }
            }
            if(!queue.isEmpty()) {
                Project p = queue.poll();
                w += p.profit;
            } else {
                break;
            }
            k--;
        }
        return w;
    }
}
