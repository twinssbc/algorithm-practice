import java.util.ArrayList;
import java.util.List;

public class Solution_43 {
    //multiply strings
    public String multiply(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        StringBuilder sb1 = new StringBuilder(num1);
        StringBuilder sb2 = new StringBuilder(num2);
        sb1.reverse();
        sb2.reverse();

        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < num1.length(); i++) {
            List<Integer> temp = multiplyOneDigit(sb2, sb1.charAt(i) - '0', i);
            addString(result, temp);
        }
        StringBuilder sb = new StringBuilder();
        for(Integer n: result) {
            sb.append(n);
        }
        sb.reverse();
        return sb.toString();
    }

    private List<Integer> multiplyOneDigit(StringBuilder num1, int num2, int zeros ) {
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < zeros; i++) {
            result.add(0);
        }

        int carry = 0;
        for(int i = 0; i < num1.length(); i++) {
            int n = num1.charAt(i)-'0';
            int total = n * num2 + carry;
            result.add(total%10);
            carry = total / 10;
        }

        if(carry != 0) {
            result.add(carry);
        }
        return result;
    }

    private void addString(List<Integer> result, List<Integer> addition) {
        int i = 0;
        int carry = 0;
        while(i < result.size() && i < addition.size()) {
            int sum = result.get(i) + addition.get(i) + carry;
            result.set(i, sum % 10);
            carry = sum/10;
            i++;
        }
        while(i < addition.size()) {
            int sum = addition.get(i) + carry;
            result.add(sum%10);
            carry = sum/10;
            i++;
        }
        while(i < result.size()) {
            int sum = result.get(i) + carry;
            result.set(i, sum%10);
            carry = sum/10;
            i++;
        }
        if(carry == 1) {
            result.add(1);
        }
    }
}
