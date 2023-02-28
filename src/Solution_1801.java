import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// Number of Orders in the Backlog
public class Solution_1801 {
    class Order {
        private int price;
        private int amount;
        private int orderType;
        public Order(int price, int amount, int orderType) {
            this.price = price;
            this.amount = amount;
            this.orderType = orderType;
        }
    }
    public int getNumberOfBacklogOrders(int[][] orders) {
        Queue<Order> minSellOrder = new PriorityQueue<>(Comparator.comparingInt(o -> o.price));
        Queue<Order> maxBuyOrder = new PriorityQueue<>((o1, o2) -> o2.price - o1.price);

        for(int[] order : orders){
            if(order[2] == 0) {
                while(order[1] >= 0 && !minSellOrder.isEmpty()) {
                    Order firstOrder = minSellOrder.peek();
                    if(order[0] >= firstOrder.price) {
                        if(order[1] >= firstOrder.amount) {
                            order[1]-= firstOrder.amount;
                            minSellOrder.poll();
                        } else {
                            firstOrder.amount -= order[1];
                            order[1]=0;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if(order[1] > 0) {
                    maxBuyOrder.add(new Order(order[0], order[1], 0));
                }
            } else {
                while(order[1] >= 0 && !maxBuyOrder.isEmpty()) {
                    Order firstOrder = maxBuyOrder.peek();
                    if(order[0] <= firstOrder.price) {
                        if(order[1] >= firstOrder.amount) {
                            order[1]-= firstOrder.amount;
                            maxBuyOrder.poll();
                        } else {
                            firstOrder.amount -= order[1];
                            order[1] = 0;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if(order[1] > 0) {
                    minSellOrder.add(new Order(order[0], order[1], 1));
                }
            }
        }

        int sum = 0;
        while(!minSellOrder.isEmpty()) {
            sum = (sum + (minSellOrder.poll().amount % 1000_000_007)) % 1000_000_007;
        }
        while(!maxBuyOrder.isEmpty()) {
            sum = (sum + (maxBuyOrder.poll().amount % 1000_000_007)) % 1000_000_007;
        }
        return sum;
    }
}
