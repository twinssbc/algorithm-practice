import java.util.ArrayList;
import java.util.List;

public class Solution_706 {
    // Design HashMap
    private List<Bucket> buckets;
    private int modulo = 2069;

    class Bucket {
        private List<int[]> data;

        public Bucket() {
            data = new ArrayList<int[]>();
        }

        public int get(int key) {
            for (int[] d : data) {
                if (d[0] == key) {
                    return d[1];
                }
            }
            return -1;
        }

        public void update(int key, int value) {
            for (int[] d : data) {
                if (d[0] == key) {
                    d[1] = value;
                    return;
                }
            }
            data.add(new int[]{key, value});
        }

        public void remove(int key) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0] == key) {
                    data.remove(i);
                    return;
                }
            }
        }
    }

    public Solution_706() {
        buckets = new ArrayList<Bucket>();
        for (int i = 0; i < modulo; i++) {
            buckets.add(new Bucket());
        }
    }

    public void put(int key, int value) {
        int hashKey = getHashKey(key);
        buckets.get(hashKey).update(key, value);
    }

    public int get(int key) {
        int hashKey = getHashKey(key);
        return buckets.get(hashKey).get(key);
    }

    public void remove(int key) {
        int hashKey = getHashKey(key);
        buckets.get(hashKey).remove(key);
    }

    private int getHashKey(int key) {
        return key % modulo;
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */

