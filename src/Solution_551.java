public class Solution_551 {
    public boolean checkRecord(String s) {
        int absentCount = 0;
        int lateCount = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == 'A') {
                lateCount = 0;
                absentCount++;
                if(absentCount >= 2) {
                    return false;
                }
            } else if(s.charAt(i) == 'L') {
                lateCount++;
                if(lateCount >= 3) {
                    return false;
                }
            } else if(s.charAt(i) == 'P') {
                lateCount = 0;
            }
        }
        return true;
    }
}
