import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class TopKStructure {
    private final int k;
    private PriorityQueue<Event> minHeap = new PriorityQueue<>((e1, e2) -> e1.getCount() - e2.getCount());
    private HashMap<String, Event> mappedEvents = new HashMap<>();

    public TopKStructure(int k) {
        this.k = k;
    }

    public void processEventCounts(Event[] events) {
        for(Event e : events) {
            if(!mappedEvents.containsKey(e.getEventKey())) {
                if(minHeap.size() < k) {
                    minHeap.add(e);
                } else if(minHeap.peek().getCount() < e.getCount()) {
                    Event toRemove = minHeap.poll();
                    mappedEvents.remove(toRemove.getEventKey());
                    minHeap.add(e);
                }
                mappedEvents.put(e.getEventKey(), e);
            } else {
                minHeap.remove(mappedEvents.get(e.getEventKey()));
                minHeap.add(e);
            }
        }
    }

    public Event[] getTopK() {
        Event[] result = new Event[minHeap.size()];
        int index = minHeap.size() - 1;
        for(Event e: minHeap) {
            result[index--] = e;
        }
        return result;
    }

    public static void main(String[] args) {
        Event[] topKEvent;
        TopKStructure structure = new TopKStructure(2);
        topKEvent = structure.getTopK();
        assert Arrays.equals(topKEvent, new Event[] {});

        structure.processEventCounts(new Event[] { new Event("A", 23)});
        topKEvent = structure.getTopK();
        assert Arrays.equals(topKEvent, new Event[] { new Event("A", 23)});

        structure.processEventCounts(new Event[] { new Event("B", 5)});
        topKEvent = structure.getTopK();
        assert Arrays.equals(topKEvent, new Event[] { new Event("A", 23), new Event("B", 5)});

        structure.processEventCounts(new Event[] { new Event("A", 30)});
        topKEvent = structure.getTopK();
        assert Arrays.equals(topKEvent, new Event[] { new Event("A", 30), new Event("B", 5)});

        structure.processEventCounts(new Event[] { new Event("B", 40), new Event("C", 32), new Event("D",12)});
        topKEvent = structure.getTopK();
        assert Arrays.equals(topKEvent, new Event[] { new Event("B", 40), new Event("C", 32)});
    }
}
