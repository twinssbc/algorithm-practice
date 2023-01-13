import java.util.HashSet;
import java.util.Set;

public class Solution_753 {
    public String crackSafe(int n, int k) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append("0");
        }
        Set<String> visitedNodes = new HashSet<>();
        String firstNode = sb.toString();
        visitedNodes.add(firstNode);
        int total = (int)Math.pow(k, n);
        dfs(firstNode, sb, visitedNodes, total, k);
        return sb.toString();
    }

    private boolean dfs(String node, StringBuilder sb, Set<String> visitedNodes, int total, int k) {
        if(visitedNodes.size() == total) {
            return true;
        }
        String suffix = node.substring(1);
        for(int i = 0; i < k; i++) {
            String nextNode = suffix + i;
            if(!visitedNodes.contains(nextNode)) {
                visitedNodes.add(nextNode);
                sb.append(i);
                if(dfs(nextNode, sb, visitedNodes, total, k)) {
                    return true;
                } else {
                    visitedNodes.remove(nextNode);
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution_753 solution = new Solution_753();
        String answer = solution.crackSafe(3,2);
        System.out.println(answer);
    }
}
