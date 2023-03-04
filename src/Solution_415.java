public class Solution_415 {
    // Add Strings
    public String addStrings(String num1, String num2) {
        int carry = 0;
        int i = 0;
        int tail1 = num1.length() - 1;
        int tail2 = num2.length() - 1;
        StringBuilder sb = new StringBuilder();
        while(i <= tail1 &&  i <= tail2) {
            int n1 = num1.charAt(tail1 - i) - '0';
            int n2 = num2.charAt(tail2 - i) - '0';
            if(n1 + n2 + carry > 9) {
                sb.append(n1 + n2 + carry - 10);
                carry = 1;
            } else {
                sb.append(n1 + n2 + carry);
                carry = 0;
            }
            i++;
        }

        while(i <= tail2) {
            int n2 = num2.charAt(tail2 - i) - '0';
            if(n2 + carry > 9) {
                sb.append(n2 + carry - 10);
                carry = 1;
            } else {
                sb.append(n2 + carry);
                carry = 0;
            }
            i++;
        }

        while(i <= tail1) {
            int n1 = num1.charAt(tail1 - i) - '0';
            if(n1 + carry > 9) {
                sb.append(n1 + carry - 10);
                carry = 1;
            } else {
                sb.append(n1 + carry);
                carry = 0;
            }
            i++;
        }

        if(carry == 1) {
            sb.append(1);
        }

        sb.reverse();
        return sb.toString();
    }
}
