public class BigNumber {
    private String addPositiveNumber(String num1, String num2) {
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

    private String subtractPositiveNumber(String num1, String num2) {
        boolean negative = false;
        String bigger = num1;
        String smaller = num2;
        if(num1.length() < num2.length()) {
            negative = true;
        } else if(num1.length() == num2.length()) {
            if(num1.compareTo(num2) < 0) {
                negative = true;
            }
        }

        if(negative) {
            bigger = num2;
            smaller = num1;
        }

        int i = 0;
        int borrow = 0;
        StringBuilder sb = new StringBuilder();
        while(i < smaller.length()) {
            int n1 = bigger.charAt(bigger.length() - 1 - i) - '0';
            int n2 = smaller.charAt(smaller.length() - 1 - i) - '0';
            if(n1 - borrow < n2) {
                sb.append(10 + n1 - borrow - n2);
                borrow = 1;
            } else {
                sb.append(n1 - borrow - n2);
                borrow = 0;
            }
            i++;
        }

        while(i < bigger.length()) {
            int n1 = bigger.charAt(bigger.length() - 1 - i) - '0';
            if(n1 - borrow < 0) {
                sb.append(10 + n1 - borrow);
                borrow = 1;
            } else {
                sb.append(n1 - borrow);
                borrow = 0;
            }
            i++;
        }

        if(sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }

        if(negative) {
            sb.append('-');
        }
        sb.reverse();
        return sb.toString();
    }

    public String add(String num1, String num2) {
        boolean negative1 = false;
        if(num1.charAt(0) == '-') {
            negative1 = true;
        }
        boolean negative2 = false;
        if(num2.charAt(0) == '-') {
            negative2 = true;
        }

        if(negative1 && negative2) {
            return "-" + addPositiveNumber(num1.substring(1), num2.substring(1));
        } else if(negative1 && !negative2) {
            return subtractPositiveNumber(num2, num1.substring(1));
        } else if(!negative1 && negative2) {
            return subtractPositiveNumber(num1, num2.substring(1));
        } else {
            return addPositiveNumber(num1, num2);
        }
    }

    public static void main(String[] args) {
        BigNumber bigNumber = new BigNumber();
        int n1 = 1348933;
        int n2 = 32979792;
        String result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));

        n1 = 999;
        n2 = 1;
        result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));

        n1 = 999;
        n2 = 999;
        result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));

        n1 = -999;
        n2 = -2;
        result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));

        n1 = 10000;
        n2 = -1;
        result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));

        n1 = 990234;
        n2 = -109090880;
        result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));

        n1 = -119273;
        n2 = 109;
        result = bigNumber.add(String.valueOf(n1), String.valueOf(n2));
        assert result.equals(String.valueOf(n1 + n2));
    }
}
