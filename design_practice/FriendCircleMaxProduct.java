import java.util.*;

public class FriendCircleMaxProduct {
    public class DisjointSet {
        HashMap<Integer, Integer> friendParents = new HashMap<>();
        HashMap<Integer, List<Integer>> friendGroups = new HashMap<>();
        public DisjointSet(List<int[]> friends) {
            for(int[] friend: friends) {
                int parent1 = findParent(friend[0]);
                int parent2 = findParent(friend[1]);
                join(parent1, parent2);
            }
        }

        public HashMap<Integer, List<Integer>> getFriendGroups() {
            return this.friendGroups;
        }

        private int findParent(int p) {
            if(!friendParents.containsKey(p)) {
                List<Integer> friends = new ArrayList<>();
                friends.add(p);
                friendGroups.put(p, friends);
                friendParents.put(p, p);
            }

            while(friendParents.get(p) != p) {
                p = friendParents.get(p);
            }
            return p;
        }

        private void join(int parent1, int parent2) {
            int size1 = friendGroups.get(parent1).size();
            int size2 = friendGroups.get(parent2).size();
            int biggerGroup = size1 > size2 ? parent1: parent2;
            int smallerGroup = size1 > size2 ? parent2: parent1;

            friendParents.put(smallerGroup, biggerGroup);
            for(Integer e: friendGroups.get(smallerGroup)) {
                friendGroups.get(biggerGroup).add(e);
            }
            friendGroups.remove(smallerGroup);
        }
    }

    public int process(List<int[]> friends) {
        HashMap<Integer, List<int[]>> companyFriends = new HashMap<>();
        for(int[] friend: friends) {
            if(!companyFriends.containsKey(friend[2])) {
                companyFriends.put(friend[2], new ArrayList<>());
            }
            companyFriends.get(friend[2]).add(friend);
        }

        List<List<Integer>> maxGroup = new ArrayList<>();
        int maxCount = 0;
        for(Map.Entry<Integer, List<int[]>> companyFriend: companyFriends.entrySet()) {
            DisjointSet djs = new DisjointSet(companyFriend.getValue());
            for(List<Integer> userList: djs.getFriendGroups().values()) {
                if(userList.size() > maxCount) {
                    maxGroup.clear();
                    maxGroup.add(userList);
                    maxCount = userList.size();
                } else if(userList.size() == maxCount){
                    maxGroup.add(userList);
                }
            }
        }

        int maxProduct = 0;
        for(List<Integer> userList: maxGroup) {
            Collections.sort(userList, Collections.reverseOrder());
            maxProduct = Math.max(maxProduct, userList.get(0) * userList.get(1));
        }
        return maxProduct;
    }

    public static void main(String[] args) {
        FriendCircleMaxProduct solution = new FriendCircleMaxProduct();
        List<int[]> friends = new ArrayList<>();
        friends.add(new int[] {1,2,1});
        friends.add(new int[] {2,3,1});
        friends.add(new int[] {1,4,2});
        friends.add(new int[] {3,2,3});
        friends.add(new int[] {1,5,2});
        friends.add(new int[] {2,5,3});
        friends.add(new int[] {3,5,1});
        int answer = solution.process(friends);
        assert answer == 15;

        List<int[]> friends2 = new ArrayList<>();
        friends2.add(new int[] {1,2,1});
        friends2.add(new int[] {1,4,1});
        friends2.add(new int[] {2,8,1});
        friends2.add(new int[] {3,5,1});
        friends2.add(new int[] {5,6,1});
        int answer2 = solution.process(friends2);
        assert answer2 == 32;
    }
}
