/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
//Maximum Product of Splitted Binary Tree
class Solution_1339 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int maxProduct(TreeNode root) {
        long totalSum = calcSum(root);
        return (int) (calcProduct(root, totalSum) % (1000_000_000 + 7));
    }

    public long calcSum(TreeNode node) {
        if (node == null) return 0;
        long sum = node.val + calcSum(node.left) + calcSum(node.right);
        node.val = (int) sum;
        return sum;
    }

    public long calcProduct(TreeNode node, long totalSum) {
        if (node.left == null && node.right == null) {
            return node.val;
        }
        long maxProduct = 0;
        if (node.left != null) {
            maxProduct = Math.max(node.left.val * (totalSum - node.left.val), maxProduct);
            maxProduct = Math.max(calcProduct(node.left, totalSum), maxProduct);
        }
        if (node.right != null) {
            maxProduct = Math.max(node.right.val * (totalSum - node.right.val), maxProduct);
            maxProduct = Math.max(calcProduct(node.right, totalSum), maxProduct);
        }
        return maxProduct;
    }
}