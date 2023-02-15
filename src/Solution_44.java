import java.util.HashMap;
import java.util.Map;

//Wildcard Matching
public class Solution_44 {
    private Map<String, Boolean> memo = new HashMap<>();
    public boolean isMatch(String s, String p) {
        StringBuilder sb = new StringBuilder();
        boolean hasStar = false;
        for(int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if(c != '*') {
                sb.append(c);
                hasStar = false;
            } else if(!hasStar){
                sb.append(c);
                hasStar = true;
            }
        }
        return match(s, sb.toString(), 0, 0);
    }

    public boolean match(String s, String p, int i, int j) {
        String key = key(i, j);
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        Boolean matchResult = false;
        if(i == s.length()) {
            if(j == p.length()) {
                matchResult = true;
            } else if('*' == p.charAt(j)){
                matchResult = match(s, p, i, j+1);
            } else {
                matchResult = false;
            }
        } else if(j == p.length()) {
            matchResult = false;
        } else {
            char charAtS = s.charAt(i);
            char charAtP = p.charAt(j);
            if(charAtS == charAtP || '?' == charAtP) {
                matchResult = match(s,p, i + 1, j + 1);
            } else if('*' == charAtP){
                matchResult = match(s, p, i + 1, j) || match(s, p, i+1, j+1) || match(s, p, i, j+1);
            } else {
                matchResult = false;
            }
        }
        memo.put(key, matchResult);
        return matchResult;
    }

    private String key(int i, int j) {
        return i+"_"+j;
    }
}
