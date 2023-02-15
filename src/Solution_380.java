import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Insert Delete GetRandom O(1)
public class Solution_380 {
    class RandomizedSet {
        Map<Integer, Integer> elementIndexMap = new HashMap<>();
        List<Integer> elementList = new ArrayList<>();
            int elementCount = 0;
            public RandomizedSet() {
        }

        public boolean insert(int val) {
            if(elementIndexMap.containsKey(val)) {
                return false;
            }
            elementList.add(elementCount, val);
            elementIndexMap.put(val, elementCount);
            elementCount++;
            return true;
        }

        public boolean remove(int val) {
            if(elementIndexMap.containsKey(val)) {
                int index = elementIndexMap.get(val);
                if(index != elementCount - 1) {
                    int swapElement = elementList.get(elementCount-1);
                    elementList.set(index, swapElement);
                    elementIndexMap.put(swapElement, index);
                }
                elementIndexMap.remove(val);
                elementCount--;
                return true;
            }
            return false;
        }

        public int getRandom() {
            int index = (int)(elementCount * Math.random());
            return elementList.get(index);
        }
    }

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
}
