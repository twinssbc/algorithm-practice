import java.util.*;

//Longest String Chain
public class Solution_1048 {
    public int longestStrChain(String[] words) {
        TreeMap<Integer, List<String>> lengthStringMap = new TreeMap<>();
        for(String word: words) {
            int length = word.length();
            if(!lengthStringMap.containsKey(length)) {
                lengthStringMap.put(length, new ArrayList<String>());
            }
            lengthStringMap.get(length).add(word);
        }

        Map<String, Integer> stringMaxLength = new HashMap<>();
        int maxLength = 1;
        for(Map.Entry<Integer, List<String>> lengthStringList: lengthStringMap.entrySet()) {
            int length = lengthStringList.getKey();
            int shorterLength = length - 1;
            for(String currentLengthString: lengthStringList.getValue()) {
                int currentStringMaxLength = 1;
                if(lengthStringMap.containsKey(shorterLength)) {
                    for(String shorterLengthString: lengthStringMap.get(shorterLength)) {
                        if(isPredecessor(shorterLengthString, currentLengthString)) {
                            currentStringMaxLength =  Math.max(currentStringMaxLength, stringMaxLength.get(shorterLengthString) + 1);
                        }
                    }
                }
                stringMaxLength.put(currentLengthString, currentStringMaxLength);
                if(currentStringMaxLength > maxLength) {
                    maxLength = currentStringMaxLength;
                }
            }
        }
        return maxLength;
    }

    private boolean isPredecessor(String shortString, String currentString) {
        for(int i = 0; i < currentString.length(); i++) {
            String newString = currentString.substring(0, i) + currentString.substring(i+1);
            if(newString.contains(shortString)) {
                return true;
            }
        }
        return false;
    }
}
