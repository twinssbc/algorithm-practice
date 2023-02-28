public class ImageMatching {
    public int process(int[][] image1, int[][] image2) {
        int row = image1.length;
        int col = image1[0].length;
        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                if(image1[r][c] != image2[r][c]) {
                    image2[r][c] = 2;
                }
            }
        }

        int count = 0;
        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                if(image2[r][c] == 1) {
                    if(findOne(image2, r, c, row, col)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private boolean findOne(int[][] grid, int x, int y, int row, int col) {
        if(x < 0 || x >= row || y < 0 || y >= col) {
            return true;
        }
        if(grid[x][y] == 2) {
            return false;
        } else if(grid[x][y] == 0) {
            return true;
        }
        grid[x][y] = 0;

        boolean answer = findOne(grid, x - 1, y, row, col) &&
                findOne(grid, x + 1, y, row, col) &&
                findOne(grid, x, y - 1, row, col) &&
                findOne(grid, x , y + 1, row, col);
        if(!answer) {
            grid[x][y] = 2;
        }
        return answer;
    }

    public static void main(String[] args) {
        ImageMatching im = new ImageMatching();
        int[][] image1 = new int[][] {
                {0,1,0,0,0,0,0},
                {0,1,0,1,0,1,1},
                {0,1,0,1,1,1,0},
                {0,0,0,0,1,0,0},
                {0,1,0,0,0,1,0},
                {0,0,0,1,1,1,0},
                {0,1,0,1,0,0,1}
        };
        int[][] image2 = new int[][] {
                {0,1,0,0,0,0,0},
                {0,1,0,1,1,1,1},
                {0,1,0,1,1,1,0},
                {0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0},
                {0,1,1,1,1,1,0},
                {0,1,0,0,0,0,1}
        };

        int answer = im.process(image1, image2);
        assert answer == 2;
    }
}
