import java.util.Random;

//Random Pick with Weight
public class Solution_528 {
    int[] freq;
    private Random rand = new Random();
    private int sum;
    public Solution_528(int[] w) {
        freq = new int[w.length];
        freq[0] = w[0];
        sum = w[0];
        for(int i = 1; i < w.length; i++) {
            freq[i] = freq[i-1] + w[i];
            sum+=w[i];
        }
    }

    public int pickIndex() {
        int randValue = rand.nextInt(sum) + 1;
        int l = 0;
        int r = freq.length - 1;
        while(l < r) {
            int mid = (l+r)/2;
            if(randValue > freq[mid]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        if(freq[l] >= randValue) {
            return l;
        } else {
            return l + 1;
        }
    }
}
