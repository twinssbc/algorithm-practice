import java.util.Arrays;

public class Solution_473 {
    public boolean makesquare(int[] matchsticks) {
        long totalLength = 0;
        for(int i = 0; i < matchsticks.length; i++) {
            totalLength += matchsticks[i];
        }
        if(totalLength % 4 > 0) {
            return false;
        }
        Arrays.sort(matchsticks);
        long averageLength = totalLength/4;
        int[] sideLength = new int[4];
        return checkMatchstick(matchsticks, matchsticks.length - 1, 0, sideLength, averageLength);
    }

    public boolean checkMatchstick(int[] matchsticks, int index, int side, int[] sideLength, long averageLength) {
        if(side == 4) {
            return false;
        }
        if(index < 0) {
            return sideLength[0] == averageLength && sideLength[1] == averageLength
                    && sideLength[2] == averageLength && sideLength[3] == averageLength;
        }

        if(matchsticks[index] + sideLength[side] <= averageLength) {
            sideLength[side] += matchsticks[index];
            if(checkMatchstick(matchsticks, index - 1, 0, sideLength, averageLength)) {
                return true;
            } else {
                sideLength[side] -= matchsticks[index];
                return checkMatchstick(matchsticks, index, side + 1, sideLength, averageLength);
            }
        } else {
            return checkMatchstick(matchsticks, index, side + 1, sideLength, averageLength);
        }
    }
}