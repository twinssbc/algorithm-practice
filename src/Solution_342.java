// Power Of Four
public class Solution_342 {
    public boolean isPowerOfFour(int n) {
        int countZero = 0;
        while(n > 0) {
            if((n & 1) == 1) {
                if(countZero % 2 == 0 && n == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                countZero++;
            }
            n >>= 1;
        }
        return false;
    }

    public boolean isPowerOfFour2(int num) {
        return (num > 0) && ((num & (num - 1)) == 0) && ((num & 0xaaaaaaaa) == 0);
    }
}
