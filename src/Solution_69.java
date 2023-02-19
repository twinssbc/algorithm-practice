public class Solution_69 {
    // Sqrt(x), Newton method
    // xn+1 = xn - f(xn)/f'(xn)
    // f(xn) = xn^2 - x
    // xn + 1 = 0.5 * (xn + x/xn)
    public int mySqrt(int x) {
        if(x < 1) return x;
        double x0 = x;
        double x1 = 0.5 * (x0 + x/x0);
        while(Math.abs(x0-x1) >= 1) {
            x0 = x1;
            x1 = 0.5 * (x0 + x / x0);
        }
        return (int) x1;
    }
}
