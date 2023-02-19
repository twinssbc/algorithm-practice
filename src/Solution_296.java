import java.util.Collections;
import java.util.PriorityQueue;

// Median Finder
public class Solution_296 {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;
    public Solution_296() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addNum(int num) {
        if(minHeap.isEmpty()) {
            minHeap.add(num);
            return;
        }

        if(num > minHeap.peek()) {
            minHeap.add(num);
        } else {
            maxHeap.add(num);
        }

        if(minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        } else if(maxHeap.size() - minHeap.size() > 1) {
            minHeap.add(maxHeap.poll());
        }
    }

    public double findMedian() {
        if(minHeap.size() > maxHeap.size()) return minHeap.peek();
        if(minHeap.size() < maxHeap.size()) return maxHeap.peek();
        return (minHeap.peek() + maxHeap.peek())/2.0;
    }
}
