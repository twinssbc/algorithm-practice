// Time: O(N), Space: O(1)
public class Parenthesis {
    public int process(String input) {
        int balance = 0;
        for(char c: input.toCharArray()) {
            if(c == '(') {
                balance ++;
            } else if(c == ')') {
                balance--;
            }
        }

        if(balance == 0 || Math.abs(balance) > 1) {
            return 0;
        }

        if(balance == 1) {
            StringBuilder sb = new StringBuilder();
            int i = input.length() - 1;
            while(i >= 0) {
                if(input.charAt(i) == '(') {
                    sb.append(')');
                } else {
                    sb.append('(');
                }
                i--;
            }
            input = sb.toString();
        }

        int count = 1;
        balance = 0;
        for(char c: input.toCharArray()) {
            if(c == '(') {
                balance++;
            } else {
                balance--;
            }
            if(balance == -1) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Parenthesis solution = new Parenthesis();
        int answer;
        answer = solution.process("()())");
        assert answer == 5;
        answer = solution.process("()()()");
        assert answer == 0;
        answer = solution.process("");
        assert answer == 0;
        answer = solution.process(")()()");
        assert answer == 1;
        answer = solution.process("()()(");
        assert answer == 1;
        answer = solution.process("((())");
        assert answer == 5;
        answer = solution.process("(((())");
        assert answer == 0;
        answer = solution.process(")(())(");
        assert answer == 0;
        answer = solution.process(")())");
        assert answer == 0;
        answer = solution.process("()())()");
        assert answer == 5;
    }
}
