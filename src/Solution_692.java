import java.util.*;

// Top K Frequent Words
public class Solution_692 {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> wordCountMap = new HashMap<>();
        for(String word: words) {
            wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> queue = new PriorityQueue<>((w1, w2) -> wordCountMap.get(w1) == wordCountMap.get(w2)? w2.compareTo(w1) : wordCountMap.get(w1) - wordCountMap.get(w2));

        for(String word: wordCountMap.keySet()) {
            queue.offer(word);
            if(queue.size() > k) {
                queue.poll();
            }
        }

        List<String> answers = new ArrayList<>();
        while(queue.size() > 0) {
            answers.add(queue.poll());
        }
        Collections.reverse(answers);
        return answers;
    }
}
