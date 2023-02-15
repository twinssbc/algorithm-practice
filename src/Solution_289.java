// Game of Life
public class Solution_289 {
    public void gameOfLife(int[][] board) {
        int row = board.length;
        int col = board[0].length;

        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                int liveCount = 0;
                int deadCount = 0;
                for(int i = Math.max(0, r - 1); i <= Math.min(r+1, row-1); i++) {
                    for(int j = Math.max(0, c-1); j <= Math.min(c+1, col-1); j++) {
                        if(i!=r || j!=c) {
                            if(isOriDead(board[i][j])) {
                                deadCount++;
                            } else {
                                liveCount++;
                            }
                        }
                    }
                }
                if(board[r][c] == 0) {
                    if(liveCount == 3) {
                        board[r][c] = 3;
                    }
                } else {
                    if(liveCount < 2 || liveCount > 3) {
                        board[r][c] = 2;
                    }
                }
            }
        }

        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                if(board[r][c]==2) {
                    board[r][c]=0;
                } else if(board[r][c]==3) {
                    board[r][c]=1;
                }
            }
        }
    }

    // 2 live -> dead
    // 3 dead -> live
    private boolean isOriDead(int number) {
        return number == 0 || number == 3;
    }
}
