import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Solution_1172 {
    private List<List<Integer>> plateStack;
    private int capacity;
    //store index in plateStack
    private TreeSet<Integer> availableStacks;
    //store index in plateStack
    private TreeSet<Integer> nonEmptyStacks;

    public Solution_1172(int capacity) {
        plateStack = new ArrayList<>();
        availableStacks = new TreeSet<>();
        nonEmptyStacks = new TreeSet<>();
        this.capacity = capacity;
    }

    public void push(int val) {
        List<Integer> toAddStack;
        if (availableStacks.size() == 0) {
            toAddStack = new ArrayList<>();
            plateStack.add(toAddStack);
            toAddStack.add(val);
            if (toAddStack.size() < capacity) {
                availableStacks.add(plateStack.size() - 1);
            }
            nonEmptyStacks.add(plateStack.size() - 1);
        } else {
            Integer toAddStackIndex = availableStacks.first();
            toAddStack = plateStack.get(toAddStackIndex);
            toAddStack.add(val);
            if (toAddStack.size() == capacity) {
                availableStacks.remove(toAddStackIndex);
            }
            nonEmptyStacks.add(toAddStackIndex);
        }
    }

    public int pop() {
        if (nonEmptyStacks.size() == 0) {
            return -1;
        }
        Integer toPopStackIndex = nonEmptyStacks.last();
        List<Integer> toPopStack = plateStack.get(toPopStackIndex);
        Integer element = toPopStack.get(toPopStack.size() - 1);
        toPopStack.remove(element);
        if (toPopStack.size() == 0) {
            nonEmptyStacks.remove(toPopStackIndex);
        }
        availableStacks.add(toPopStackIndex);
        return element;
    }

    public int popAtStack(int index) {
        if (plateStack.size() <= index) {
            return -1;
        }
        List<Integer> toPopStack = plateStack.get(index);
        if (toPopStack.size() == 0) {
            return -1;
        }
        Integer element = toPopStack.get(toPopStack.size() - 1);
        toPopStack.remove(element);
        if (toPopStack.size() == 0) {
            nonEmptyStacks.remove(index);
        }
        availableStacks.add(index);

        return element;
    }

    /**
     * Your DinnerPlates object will be instantiated and called as such:
     * DinnerPlates obj = new DinnerPlates(capacity);
     * obj.push(val);
     * int param_2 = obj.pop();
     * int param_3 = obj.popAtStack(index);
     */
    public static void main(String[] args) {
        Solution_1172 D = new Solution_1172(1);
        D.push(1);
        D.push(2);
        D.push(3);
        D.push(4);
        D.push(5);
        D.popAtStack(0);
        D.push(20);
        D.push(21);
        D.popAtStack(0);
        D.popAtStack(2);
        D.pop();
        D.pop();
        D.pop();
        D.pop();
        D.pop();
    }
}
