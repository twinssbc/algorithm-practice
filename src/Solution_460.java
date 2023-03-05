import java.util.HashMap;
import java.util.LinkedHashSet;

// LFU Cache
class Solution_460 {
    // Key: key, Value: frequency-value
    private HashMap<Integer, int[]> keyFrequency;
    // Key: frequency, Value: keys
    private HashMap<Integer, LinkedHashSet<Integer>> frequencyKeysMap;
    private int capacity;
    private int minFrequency;
    private int size;
    public Solution_460(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 1;
        this.size = 0;
        keyFrequency = new HashMap<>();
        frequencyKeysMap = new HashMap<>();
    }

    public int get(int key) {
        if(keyFrequency.containsKey(key)) {
            int[] pair = keyFrequency.get(key);
            int frequency = pair[0];
            pair[0]++;
            frequencyKeysMap.get(frequency).remove(key);
            if(frequencyKeysMap.get(frequency).isEmpty()) {
                frequencyKeysMap.remove(frequency);
                if(frequency == minFrequency) {
                    minFrequency++;
                }
            }
            if(!frequencyKeysMap.containsKey(frequency+1)) {
                frequencyKeysMap.put(frequency+1, new LinkedHashSet<>());
            }
            frequencyKeysMap.get(frequency+1).add(key);
            return pair[1];
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if(keyFrequency.containsKey(key)) {
            int[] pair = keyFrequency.get(key);
            int frequency = pair[0];
            pair[0]++;
            pair[1] = value;
            frequencyKeysMap.get(frequency).remove(key);
            if(frequencyKeysMap.get(frequency).isEmpty()) {
                frequencyKeysMap.remove(frequency);
                if(frequency == minFrequency) {
                    minFrequency++;
                }
            }

            if(!frequencyKeysMap.containsKey(frequency+1)) {
                frequencyKeysMap.put(frequency+1, new LinkedHashSet<>());
            }
            frequencyKeysMap.get(frequency+1).add(key);
        } else {
            if(size == capacity) {
                int firstKey = frequencyKeysMap.get(minFrequency).iterator().next();
                keyFrequency.remove(firstKey);
                frequencyKeysMap.get(minFrequency).remove(firstKey);
                if(frequencyKeysMap.get(minFrequency).isEmpty()) {
                    frequencyKeysMap.remove(minFrequency);
                }
                size--;
            }
            if(!frequencyKeysMap.containsKey(1)) {
                frequencyKeysMap.put(1, new LinkedHashSet<>());
            }
            frequencyKeysMap.get(1).add(key);
            minFrequency = 1;
            keyFrequency.put(key, new int[]{1, value});
            size++;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */