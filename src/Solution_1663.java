class Solution_1663 {
    public int maxRepeating(String sequence, String word) {
        int sIndex = 0;
        int wIndex = 0;
        int maxK = 0;
        int currentK = 0;
        int matchPosition = -1;
        while(sIndex < sequence.length()) {
            if(sequence.charAt(sIndex) == word.charAt(wIndex)) {
                if (matchPosition == -1) {
                    matchPosition = sIndex;
                }
                wIndex++;
                sIndex++;
                if(wIndex == word.length()) {
                    wIndex = 0;
                    currentK++;
                    if (currentK > maxK) {
                        maxK = currentK;
                    }
                }
            } else {
                wIndex = 0;
                currentK = 0;
                if (matchPosition >= 0) {
                    sIndex = matchPosition + 1;
                } else {
                    sIndex++;
                }
                matchPosition = -1;
            }
        }
        return maxK;
    }

    public static void main(String[]  args) {
        String sequence = "aaabaaaabaaabaaaabaaaabaaaabaaaaba";
        String word = "aaaba";
        Solution_1663 s = new Solution_1663();
        int k = (s.maxRepeating(sequence, word));
        assert k == 5: "wrong k";
    }
}