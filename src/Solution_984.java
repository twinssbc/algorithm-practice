public class Solution_984 {
    public String strWithout3a3b(int a, int b) {
        StringBuilder sb = new StringBuilder();
        char first;
        char second;
        int firstCount;
        int secondCount;
        if(a > b) {
            first = 'a';
            second = 'b';
            firstCount = a;
            secondCount = b;
        } else {
            first = 'b';
            second = 'a';
            firstCount = b;
            secondCount = a;
        }

        while(firstCount > secondCount && firstCount > 0 && secondCount > 0) {
            sb.append(first);
            sb.append(first);
            firstCount -= 2;
            sb.append(second);
            secondCount -= 1;
        }

        while(firstCount > 0 || secondCount > 0) {
            if(firstCount > 0) {
                sb.append(first);
                firstCount--;
            }
            if(secondCount > 0) {
                sb.append(second);
                secondCount--;
            }
        }
        return sb.toString();
    }
}
