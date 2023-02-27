import java.util.*;

public class InviteFriends_Memo {
    private HashMap<Integer, Integer> memo;
    public int inviteMostFriends(List<int[]> friendList, int num) {
        memo = new HashMap<>();
        HashMap<Integer, List<Integer>> friendConnections = new HashMap<>();
        for(int[] friendPair: friendList) {
            if(!friendConnections.containsKey(friendPair[0])) {
                friendConnections.put(friendPair[0], new ArrayList<>());
            }
            friendConnections.get(friendPair[0]).add(friendPair[1]);
            if(!friendConnections.containsKey(friendPair[1])) {
                friendConnections.put(friendPair[1], new ArrayList<>());
            }
            friendConnections.get(friendPair[1]).add(friendPair[0]);
        }

        int remaining = 0;
        for(int i = 1; i <= num; i++) {
            remaining |= (1 << i);
        }

        return helper(friendConnections, remaining, num);
    }

    public int helper(HashMap<Integer, List<Integer>> friendConnections, Integer remaining, int num) {
        if(remaining == 0) {
            return 0;
        }

        if(memo.containsKey(remaining)) {
            return memo.get(remaining);
        }

        int maxCount = 0;
        for(int i = 1; i <= num; i++) {
            if(((remaining >> i) & 1) != 0) {
                int newRemaining = remaining ^ (1 << i);
                if (friendConnections.containsKey(i)) {
                    for (Integer friend : friendConnections.get(i)) {
                        if(((newRemaining >> friend) & 1) != 0) {
                            newRemaining ^= (1 << friend);
                        }
                    }
                }
                maxCount = Math.max(maxCount, helper(friendConnections, newRemaining, num) + 1);
            }
        }
        memo.put(remaining, maxCount);
        return maxCount;
    }

    public static void main(String[] args) {
        InviteFriends_Memo solution = new InviteFriends_Memo();

        List<int[]> friends = new ArrayList<>();
        friends.add(new int[] {1,2});
        friends.add(new int[] {1,3});
        friends.add(new int[] {4,5});
        friends.add(new int[] {3,5});

        int answer = solution.inviteMostFriends(friends,5);
        assert answer==3;

        friends.clear();
        friends.add(new int[] {1,3});
        friends.add(new int[] {1,4});
        friends.add(new int[] {1,2});
        answer = solution.inviteMostFriends(friends,4);
        assert answer==3;

        friends.clear();
        friends.add(new int[] {1, 2});
        friends.add(new int[] {1, 5});
        friends.add(new int[] {2, 3});
        friends.add(new int[] {2, 4});
        friends.add(new int[] {3, 5});
        friends.add(new int[] {3, 8});
        friends.add(new int[] {4, 7});
        friends.add(new int[] {4, 5});
        friends.add(new int[] {5, 6});
        friends.add(new int[] {5, 7});
        friends.add(new int[] {5, 8});
        friends.add(new int[] {6, 9});
        friends.add(new int[] {7, 9});
        friends.add(new int[] {8, 9});
        friends.add(new int[] {8, 10});
        friends.add(new int[] {9, 10});
        answer = solution.inviteMostFriends(friends, 10);
        assert answer == 5;
    }
}
