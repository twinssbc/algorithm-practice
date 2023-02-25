import java.util.*;

public class StreamMerger {
    private class Data {
        int value;
        int timestamp;
        int stream;

        public Data(int value, int timestamp, int stream) {
            this.value = value;
            this.timestamp = timestamp;
            this.stream = stream;
        }

        public int getValue() {
            return value;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public int getStream() {
            return stream;
        }

        public void setValue(int v) {
            this.value = v;
        }
    }
    // min heap
    private final PriorityQueue<Data> heap = new PriorityQueue<>(Comparator.comparingInt(Data::getTimestamp));
    private final List<Integer> streamPosition = new ArrayList<>();
    private final Map<Integer, Data> streamLastElement = new HashMap<>();
    private final List<List<int[]>> streams = new ArrayList<>();
    private Data lastElement;
    public void mergeStream(List<int[]> stream) {
        streams.add(stream);
        if(stream.size() > 0) {
            Data lastElement = new Data(stream.get(0)[0], stream.get(0)[1], streams.size() - 1);
            heap.add(lastElement);
            streamPosition.add(1);
            streamLastElement.put(streams.size() - 1, lastElement);
        } else {
            streamPosition.add(0);
        }
    }

    public int[] getNextElement() {
        int[] result = new int[2];
        Data nextElement = heap.poll();
        if(nextElement != null) {
            result[0] = nextElement.getValue();
            result[1] = nextElement.getTimestamp();

            if (lastElement != null) {
                result[0] = result[0] - lastElement.getValue();
            }
            lastElement = nextElement;

            // add next element in the stream
            int streamIndex = lastElement.getStream();
            if(streamPosition.get(streamIndex) < streams.get(streamIndex).size()) {
                int[] toAddElement = streams.get(streamIndex).get(streamPosition.get(streamIndex));
                Data toAddData = new Data(toAddElement[0], toAddElement[1], streamIndex);

                if(streamPosition.get(streamIndex) > 0) {
                    toAddData.setValue(toAddData.getValue() + streamLastElement.get(streamIndex).getValue());
                }
                streamLastElement.put(streamIndex, toAddData);
                streamPosition.set(streamIndex, streamPosition.get(streamIndex) + 1);
                heap.add(toAddData);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        StreamMerger sm = new StreamMerger();
        List<int[]> stream0 = new ArrayList<>();
        stream0.add(new int[] {10, 0});
        stream0.add(new int[] {-5, 6});

        List<int[]> stream1 = new ArrayList<>();
        stream1.add(new int[] {17, 2});

        List<int[]> stream2 = new ArrayList<>();
        stream2.add(new int[] {15, 1});
        stream2.add(new int[] {5, 4});
        stream2.add(new int[] {1, 8});
        sm.mergeStream(stream0);
        sm.mergeStream(stream1);
        sm.mergeStream(stream2);

        int[] result;
        result = sm.getNextElement();
        assert Arrays.equals(result, new int[] {10, 0});

        result = sm.getNextElement();
        assert Arrays.equals(result,  new int[] {5, 1});

        result = sm.getNextElement();
        assert Arrays.equals(result,  new int[] {2, 2});

        result = sm.getNextElement();
        assert Arrays.equals(result,  new int[] {3, 4});

        result = sm.getNextElement();
        assert Arrays.equals(result,  new int[] {-15, 6});

        result = sm.getNextElement();
        assert Arrays.equals(result,  new int[] {16, 8});
    }
}
