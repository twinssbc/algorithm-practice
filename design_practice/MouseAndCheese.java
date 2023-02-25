import java.util.Random;

public class MouseAndCheese {
    public static void main(String[] args) {
        MouseAndCheeseGameServer gs = new MouseAndCheeseGameServer();
        MouseAndCheeseGameServer.AI ai1 = gs.join("1", "player1");
        MouseAndCheeseGameServer.AI ai2 = gs.join("1", "player2");
        gs.startGame("1");

        int move = 10000;
        Random rand = new Random();
        while(move >= 0) {
            if(ai1.isMyTurn()) {
                System.out.println("ai1 distance: " + ai1.getDistance());
                MouseAndCheeseGameServer.Location currentLocation = ai1.getLocation();
                System.out.println("ai1 location: " + currentLocation);
                int direction = rand.nextInt(4);
                if(direction == 0) {
                    ai1.move(currentLocation.getX() - 1, currentLocation.getY());
                } else if(direction == 1) {
                    ai1.move(currentLocation.getX() + 1, currentLocation.getX());
                } else if(direction == 2) {
                    ai1.move(currentLocation.getX(), currentLocation.getY() - 1 );
                } else if(direction == 3) {
                    ai1.move(currentLocation.getX(), currentLocation.getY() + 1);
                }
            }
            if(ai2.isMyTurn()) {
                System.out.println("ai2 distance: " + ai2.getDistance());
                MouseAndCheeseGameServer.Location currentLocation = ai2.getLocation();
                System.out.println("ai2 location: " + currentLocation);
                int direction = rand.nextInt(4);
                if(direction == 0) {
                    ai2.move(currentLocation.getX() - 1, currentLocation.getY());
                } else if(direction == 1) {
                    ai2.move(currentLocation.getX() + 1, currentLocation.getY());
                } else if(direction == 2) {
                    ai2.move(currentLocation.getX(), currentLocation.getY() - 1 );
                } else if(direction == 3) {
                    ai2.move(currentLocation.getX(), currentLocation.getY() + 1);
                }
            }

            if(gs.getWinner("1") != null) {
                System.out.println("Game end, winner is " + gs.getWinner("1"));
                break;
            }
            move--;
        }
    }
}
