import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CurrencyExchange_Memo {
    private HashMap<String, Double> memo;
    public double process(double[][] currency, int startIndex, int endIndex) {
        memo = new HashMap<>();
        int bitmap = 0;
        bitmap |= (1 << startIndex);
        return helper(currency, bitmap, startIndex, endIndex);
    }

    private double helper(double[][] currency, int bitmap, int startIndex, int endIndex) {
        if(memo.containsKey(bitmap+ "_" + startIndex)) {
            return memo.get(bitmap + "_" + startIndex);
        }

        if(startIndex == endIndex) {
            return currency[startIndex][endIndex];
        }

        double maxRate = 0;
        for(int i = 0; i < currency.length; i++) {
            if(((bitmap & (1 << i)) == 0) && i != startIndex) {
                bitmap |= (1 << i);
                maxRate = Math.max(maxRate, currency[startIndex][i] * helper(currency, bitmap, i, endIndex));
                bitmap ^= (1 << i);
            }
        }
        memo.put(bitmap + "_" + startIndex, maxRate);
        return maxRate;
    }

    public static void main(String[] args) {
        double[][] currency = new double[][] {{1, 0.6, 0.8}, {1.2, 1, 1}, {1.3, 1.3, 1}};
        CurrencyExchange_Memo ce = new CurrencyExchange_Memo();
        double result = ce.process(currency, 2, 0);
        assert result == 1.56;

        double[][] currency2 = new double[][] {{1, 1.3, 1, 6.49}, {0.72, 1, 0.9, 5.5}, {1.1, 1.1, 1, 7.3}, {0.18, 0.2, 0.136, 1}};
        double result2 = ce.process(currency2, 2, 1);
        assert result2 == 1.7082;
    }
}
