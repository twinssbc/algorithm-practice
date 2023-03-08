import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Word Search II, Trie
// Time: O(M(4* 3^(L-1))), M: number of Cells, L: maximum length of words
// Space: O(N)
public class Solution_212 {
    private char[][] board;
    private List<String> result;
    private boolean[][] visited;
    private int totalRow;
    private int totalCol;
    class TrieNode {
        HashMap<Character, TrieNode> children;
        String word;

        public TrieNode() {
            children = new HashMap<>();
        }
    }

    public List<String> findWords(char[][] board, String[] words) {
        this.board = board;
        this.result = new ArrayList<>();
        totalRow = board.length;
        totalCol = board[0].length;
        visited = new boolean[totalRow][totalCol];
        TrieNode root = new TrieNode();

        // contract Trie
        for(String word: words) {
            TrieNode node = root;
            for(char c: word.toCharArray()) {
                if(!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }
                node = node.children.get(c);
            }
            node.word = word;
        }

        for(int i = 0; i < totalRow; i++) {
            for(int j = 0; j < totalCol; j++) {
                if(root.children.containsKey(board[i][j])) {
                    backtrack(i, j, root);
                }
            }
        }
        return this.result;
    }

    public void backtrack(int row, int col, TrieNode parent) {
        char c = board[row][col];
        TrieNode node = parent.children.get(c);

        if(node.word != null) {
            //find the word in the board
            result.add(node.word);
            // clear it as no need for further calculation
            node.word = null;
        }

        visited[row][col] = true;
        int[][] deltas = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for(int[] delta: deltas) {
            int newrow = row + delta[0];
            if(newrow < 0 || newrow >= totalRow) continue;
            int newcol = col + delta[1];
            if(newcol < 0 || newcol >= totalCol) continue;
            if(!visited[newrow][newcol]) {
                if(node.children.containsKey(board[newrow][newcol])) {
                    backtrack(newrow, newcol, node);
                }
            }
        }
        visited[row][col] = false;
        // node is already the leaf and has been traversed, remove it
        if(node.children.isEmpty()) {
            parent.children.remove(node);
        }
    }
}
