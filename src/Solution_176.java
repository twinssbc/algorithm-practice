import java.util.HashMap;

public class Solution_176 {
    // LRU Cache
    class DNode {
        DNode prev;
        DNode next;
        int key;
        int value;
        public DNode() {
        }
    }
    private int capacity;
    private int size = 0;
    private DNode head;
    private DNode tail;
    private HashMap<Integer, DNode> cache;

    public Solution_176(int capacity) {
        this.capacity = capacity;
        head = new DNode();
        tail = new DNode();
        head.next = tail;
        tail.prev = head;
        cache = new HashMap<>();
    }

    private void moveToHead(DNode node) {
        DNode prev = node.prev;
        DNode next = node.next;
        prev.next = next;
        next.prev = prev;
        addToHead(node);
    }

    private void addToHead(DNode node) {
        node.next = head.next;
        node.prev = head;
        node.next.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if(cache.containsKey(key)) {
            DNode node = cache.get(key);
            moveToHead(node);
            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if(cache.containsKey(key)) {
            DNode node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            DNode newNode = new DNode();
            newNode.key = key;
            newNode.value = value;
            cache.put(key, newNode);

            addToHead(newNode);
            size++;
            if(size > capacity) {
                DNode prev = tail.prev;
                prev.prev.next = tail;
                tail.prev = prev.prev;
                cache.remove(prev.key);
                size--;
            }
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */