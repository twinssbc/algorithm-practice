public class Solution_44_Backtracking {
    public boolean isMatch(String s, String p) {
        int sIndex = 0;
        int pIndex = 0;
        int sLength = s.length();
        int pLength = p.length();
        int starIndex = -1;
        int sCheckIndex = -1;
        while(sIndex < sLength) {
            if(pIndex < pLength && (s.charAt(sIndex) == p.charAt(pIndex) || '?' == p.charAt(pIndex))) {
                sIndex++;
                pIndex++;
            } else if(pIndex < pLength && '*' == p.charAt(pIndex)) {
                starIndex = pIndex;
                sCheckIndex = sIndex;
                pIndex++;
            } else if(starIndex == -1) {
                return false;
            } else {
                pIndex = starIndex+1;
                sIndex = sCheckIndex+1;
                sCheckIndex = sIndex;
            }
        }

        while(pIndex < pLength) {
            if(p.charAt(pIndex) != '*') {
                return false;
            } else {
                pIndex++;
            }
        }
        return true;
    }
}
