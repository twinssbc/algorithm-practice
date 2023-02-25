import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MouseAndCheeseGameServer {
    class AI {
        private final String id;
        private final Game game;

        private AI(String id, Game game) {
            this.id = id;
            this.game = game;
        }

        public Location getLocation() {
            return game.getLocation(id);
        }

        // invalid move: -1, valid move: 0, game lose: 1, game win: 2
        public int move(int x, int y) {
            return game.move(id, new Location(x, y));
        }

        public int getDistance() {
            return game.getDistance(id);
        }

        public boolean isMyTurn() {
            return game.isTurn(id);
        }
    }

    class Location {
        private final int x;
        private final int y;
        private Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }

    private class Game {
        private static final int MAX_PLAYER = 4;
        private final int size;
        private boolean playing;
        private int currentPlayerIndex;
        private final HashMap<String, Location> playerLocation;
        private final List<String> playerIds;
        private Location cheese;
        private String winner;
        public Game(int size) {
            this.size = size;
            playerLocation = new HashMap<>();
            playerIds = new ArrayList<>();
            currentPlayerIndex = 0;
        }

        public AI join(String playerId) {
            if(playing) {
                throw new RuntimeException("Game is playing");
            }
            if(playerLocation.size() == MAX_PLAYER) {
                throw new RuntimeException("Already full");
            }

            if(playerLocation.containsKey(playerId)) {
                throw new RuntimeException("Already joined");
            }

            AI player = new AI(playerId, this);
            playerIds.add(playerId);
            playerLocation.put(playerId, new Location(0, 0));
            return player;
        }

        public void start() {
            Random rand = new Random();
            cheese = new Location(rand.nextInt(size), rand.nextInt(size));
            //debug purpose
            System.out.println("cheese location: " + cheese);
            for(String playerId: playerLocation.keySet()) {
                while(true) {
                    Location randLocation = new Location(rand.nextInt(size), rand.nextInt(size));
                    if(randLocation != cheese) {
                        playerLocation.put(playerId, randLocation);
                        break;
                    }
                }
            }
            playing = true;
            winner = null;
        }

        public Location getLocation(String playerId) {
            Location loc = playerLocation.get(playerId);
            if(loc == null) {
                throw new RuntimeException("Invalid user: " + playerId);
            }
            return loc;
        }

        public int getDistance(String playerId) {
            Location loc = playerLocation.get(playerId);
            if(loc == null) {
                throw new RuntimeException("Invalid user: " + playerId);
            }
            return Math.abs(loc.getX()-cheese.getX()) + Math.abs(loc.getY() - cheese.getY());
        }

        // invalid move: -1, valid move: 0, game win: 1
        public int move(String playerId, Location location) {
            if(playerId != playerIds.get(currentPlayerIndex)) {
                System.out.println("Not your turn: " + playerId);
                return -1;
            }

            if(location.getX() < 0 || location.getX() >= size || location.getY() < 0 || location.getY() >= size) {
                System.out.println("Invalid move: " + playerId);
                return -1;
            }

            Location currentLoc = playerLocation.get(playerId);
            if(Math.abs(currentLoc.getX() - location.getX()) + Math.abs(currentLoc.getY() - location.getY()) > 1) {
                System.out.println("Invalid move: " + playerId);
                return -1;
            }

            playerLocation.put(playerId, location);
            currentPlayerIndex++;
            if(currentPlayerIndex >= playerIds.size()) {
                currentPlayerIndex = 0;
            }

            if(getDistance(playerId) == 0) {
                winner = playerId;
                playing = false;
                return 1;
            }
            return 0;
        }

        public String getWinner() {
            return winner;
        }

        public boolean isTurn(String playerId) {
            return playerIds.get(currentPlayerIndex) == playerId;
        }
    }

    private final HashMap<String, Game> games = new HashMap<>();
    private final static int BOARD_SIZE = 10;
    public MouseAndCheeseGameServer() {}
    public AI join(String gameId, String playerId) {
        if(!games.containsKey(gameId)) {
            games.put(gameId, new Game(BOARD_SIZE));
        }
        return games.get(gameId).join(playerId);
    }

    public boolean startGame(String gameId) {
        Game game = games.get(gameId);
        if(game == null) {
            System.out.println("Game doesn't exist: " + gameId);
            return false;
        }
        game.start();
        return true;
    }

    public String getWinner(String gameId) {
        Game game = games.get(gameId);
        if(game == null) {
            System.out.println("Game doesn't exist: " + gameId);
            return null;
        }
        return game.getWinner();
    }
}
