public class Solution_812 {
    public double largestTriangleArea(int[][] points) {
        double maxArea = 0.0;
        for(int i = 0; i < points.length; i++) {
            for(int j = i + 1; j < points.length; j++) {
                for(int k = j + 1; k < points.length; k++) {
                    double area = Math.abs(0.5 * (points[i][0] * (points[j][1] - points[k][1])
                            + points[j][0] * (points[k][1] - points[i][1])
                            + points[k][0] * (points[i][1] - points[j][1])));
                    if(area > maxArea) {
                        maxArea = area;
                    }
                }
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        Solution_812 solution = new Solution_812();
        int[][] points = new int[][] {{0,0}, {0,1}, {1,0}, {0,2}, {2,0}};
        double area = solution.largestTriangleArea(points);
        assert area == 2.0: "case 1";
    }
}
