import java.util.HashSet;
import java.util.Set;

public class CurrencyExchange {
    public double process(double[][] currency, int startIndex, int endIndex) {
        Set<Integer> visited = new HashSet<>();
        visited.add(startIndex);
        return helper(currency, visited, startIndex, endIndex);
    }

    private double helper(double[][] currency, Set<Integer> visited, int startIndex, int endIndex) {
        if(startIndex == endIndex) {
            return currency[startIndex][endIndex];
        }

        double maxRate = 0;
        for(int i = 0; i < currency.length; i++) {
            if(!visited.contains(i) && i != startIndex) {
                visited.add(i);
                maxRate = Math.max(maxRate, currency[startIndex][i] * helper(currency, visited, i, endIndex));
                visited.remove(i);
            }
        }
        return maxRate;
    }

    public static void main(String[] args) {
        double[][] currency = new double[][] {{1, 0.6, 0.8}, {1.2, 1, 1}, {1.3, 1.3, 1}};
        CurrencyExchange ce = new CurrencyExchange();
        double result = ce.process(currency, 2, 0);
        assert result == 1.56;

        double[][] currency2 = new double[][] {{1, 1.3, 1, 6.49}, {0.72, 1, 0.9, 5.5}, {1.1, 1.1, 1, 7.3}, {0.18, 0.2, 0.136, 1}};
        double result2 = ce.process(currency2, 2, 1);
        assert result2 == 1.7082;
    }
}
