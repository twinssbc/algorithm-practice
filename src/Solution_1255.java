public class Solution_1255 {
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        int[] letterAvail = new int[26];
        for(char c: letters) {
            letterAvail[c-'a']++;
        }
        return dfs(words, 0, letterAvail, score, 0);
    }

    public int dfs(String[] words, int wordIndex, int[] letterAvail, int[] score, int currentScore) {
        if(wordIndex == words.length) {
            return currentScore;
        }

        String word = words[wordIndex];
        int index = 0;
        int wordScore = 0;
        for(; index < word.length(); index++) {
            if(letterAvail[word.charAt(index)-'a'] > 0) {
                letterAvail[word.charAt(index)-'a']--;
                wordScore += score[word.charAt(index)-'a'];
            } else {
                break;
            }
        }

        int foundScore = 0;
        if(index == word.length()) {
            foundScore = dfs(words, wordIndex+1, letterAvail, score, currentScore + wordScore);
        }
        for(int i = 0; i < index; i++) {
            letterAvail[word.charAt(i)-'a']++;
        }
        int notFoundScore = dfs(words, wordIndex+1, letterAvail, score, currentScore);
        return Math.max(foundScore, notFoundScore);
    }
}
