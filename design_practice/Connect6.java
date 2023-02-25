import java.util.HashMap;
import java.util.Objects;

public class Connect6 {
    private class Coordinate{
        private int x;
        private int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }
            if(o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinate that = (Coordinate) o;
            return x == that.x && y== that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // black: -1, 2, 3, white, 0, 1
    private int turnCount = -1;
    private int k;
    private HashMap<Coordinate, Integer> placedChess = new HashMap<>();
    public Connect6(int k) {
        this.k = k;
    }

    public String getTurn() {
        if(turnCount == -1 || turnCount == 2 || turnCount == 3) {
            return "Black";
        } else {
            return "White";
        }
    }

    public boolean placeBlack(int x, int y) {
        if(getTurn().equals("White") || !validCheck(x, y)) {
            throw new RuntimeException("Black, Invalid Move: (" + x + ", " + y + ")");
        }
        if(checkWin(x, y, 0)) {
            return true;
        }
        placedChess.put(new Coordinate(x, y), 0);
        nextTurn();
        return false;
    }

    public boolean placeWhite(int x, int y) {
        if(getTurn().equals("Black") || !validCheck(x, y)) {
            throw new RuntimeException("White, Invalid Move: (" + x + ", " + y + ")");
        }
        if(checkWin(x, y, 1)) {
            return true;
        }
        placedChess.put(new Coordinate(x, y), 1);
        nextTurn();
        return false;
    }

    private void nextTurn() {
        turnCount++;
        if(turnCount > 3) {
            turnCount = 0;
        }
    }

    private boolean validCheck(int x, int y) {
        if(x < 0 || y < 0 || x >= k || y >= k || placedChess.containsKey(new Coordinate(x, y))) {
            return false;
        }
        return true;
    }

    private boolean checkWin(int x, int y, int player) {
        int contCount = 1;
        //check row
        if(checkContCount(x, y, 0, -1, player) + checkContCount(x, y, 0, 1, player) + 1 >= 6) {
            return true;
        }
        //check col
        if(checkContCount(x, y, -1, 0, player) + checkContCount(x, y, 1, 0, player) + 1 >= 6) {
            return true;
        }
        //check diag
        if(checkContCount(x, y, -1, -1, player) + checkContCount(x, y, 1, 1, player) + 1 >= 6) {
            return true;
        }
        //check reverse diag
        if(checkContCount(x, y, 1, -1, player) + checkContCount(x, y, -1, 1, player) + 1 >= 6) {
            return true;
        }
        return false;
    }

    private int checkContCount(int x, int y, int dx, int dy, int player) {
        int contCount = 0;
        int newx = x + dx;
        int newy = y + dy;
        while(newx >= 0 && newy >=0 && newx < k && newy < k && contCount < 6) {
            if(placedChess.getOrDefault(new Coordinate(newx, newy), -1) == player) {
                contCount++;
                newx+=dx;
                newy+=dy;
            } else {
                break;
            }
        }
        return contCount;
    }

    public static void main2(String[] args) {
        Connect6 game = new Connect6(10);
        assert game.getTurn() == "Black";
        boolean result = game.placeBlack(3, 3);
        assert result == false;

        assert game.getTurn() == "White";
        result = game.placeWhite(3, 4);
        assert result == false;

        assert game.getTurn() == "White";
        result = game.placeWhite(3, 5);
        assert result == false;

        // 4
        assert game.getTurn() == "Black";
        result = game.placeBlack(4, 3);
        assert result == false;

        // 5
        assert game.getTurn() == "Black";
        result = game.placeBlack(5, 3);
        assert result == false;

        // 6
        assert game.getTurn() == "White";
        result = game.placeWhite(4, 4);
        assert result == false;

        // 7
        assert game.getTurn() == "White";
        result = game.placeWhite(6, 3);
        assert result == false;

        // 8
        assert game.getTurn() == "Black";
        result = game.placeBlack(5, 4);
        assert result == false;

        // 9
        assert game.getTurn() == "Black";
        result = game.placeBlack(3, 2);
        assert result == false;

        // 10
        assert game.getTurn() == "White";
        result = game.placeWhite(2, 4);
        assert result == false;

        // 11
        assert game.getTurn() == "White";
        result = game.placeWhite(1, 4);
        assert result == false;

        // 12
        assert game.getTurn() == "Black";
        result = game.placeBlack(6, 5);
        assert result == false;

        // 13
        assert game.getTurn() == "Black";
        result = game.placeBlack(2, 1);
        assert result == false;

        // 14
        assert game.getTurn() == "White";
        result = game.placeWhite(7, 6);
        assert result == false;

        // 15
        assert game.getTurn() == "White";
        result = game.placeWhite(0, 4);
        assert result == false;

        // 16
        assert game.getTurn() == "Black";
        result = game.placeBlack(1, 0);
        assert result == true;
    }

    public static void main(String[] args) {
        Connect6 game = new Connect6(10);

        assert game.getTurn() == "Black";
        boolean result = game.placeBlack(1, 1);
        assert result == false;

        assert game.getTurn() == "White";
        result = game.placeWhite(1, 2);
        assert result == false;

        assert game.getTurn() == "White";
        result = game.placeWhite(2, 2);
        assert result == false;

        // 4
        assert game.getTurn() == "Black";
        result = game.placeBlack(2, 1);
        assert result == false;

        // 5
        assert game.getTurn() == "Black";
        result = game.placeBlack(3, 1);
        assert result == false;

        // 6
        assert game.getTurn() == "White";
        result = game.placeWhite(3, 2);
        assert result == false;

        // 7
        assert game.getTurn() == "White";
        result = game.placeWhite(4, 2);
        assert result == false;

        // 8
        assert game.getTurn() == "Black";
        result = game.placeBlack(4, 1);
        assert result == false;

        // 9
        assert game.getTurn() == "Black";
        result = game.placeBlack(5, 1);
        assert result == false;

        // 10
        assert game.getTurn() == "White";
        result = game.placeWhite(5, 2);
        assert result == false;

        // 11
        assert game.getTurn() == "White";
        result = game.placeWhite(6, 2);
        assert result == true;
    }
}
