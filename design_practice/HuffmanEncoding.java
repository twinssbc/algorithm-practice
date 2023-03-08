import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// Time: O(L)
// Space: O(L)
public class HuffmanEncoding {
    private class Node {
        private Character c;
        private Integer frequency;
        private Node left;
        private Node right;

        public Node(Character c, Integer frequency) {
            this.c = c;
            this.frequency = frequency;
        }

        public Node(Character c, Integer frequency, Node left, Node right) {
            this.c = c;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;
    private HashMap<Character, String> codeMap;

    public HuffmanEncoding() {
        codeMap = new HashMap<Character, String>();
    }


    public String encode(String s) {
        if(s == null || s.isEmpty()) {
            return "";
        }
        HashMap<Character, Integer> freq = new HashMap<>();
        for(Character c : s.toCharArray()) {
            if(!freq.containsKey(c)) {
                freq.put(c, 1);
            } else {
                freq.put(c, freq.get(c) + 1);
            }
        }

        PriorityQueue<Node> sortedFreq = new PriorityQueue<>(Comparator.comparingInt(n -> n.frequency));
        for(Map.Entry<Character, Integer> entry: freq.entrySet()) {
            sortedFreq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while(sortedFreq.size() > 1) {
            Node n1 = sortedFreq.poll();
            Node n2 = sortedFreq.poll();
            Node newNode = new Node(null, n1.frequency + n2.frequency, n1, n2);
            sortedFreq.add(newNode);
        }
        root = sortedFreq.poll();
        if(root.left == null && root.right == null) {
            root = new Node(null, root.frequency, root, null);
        }
        buildCodeMap(root, "");
        StringBuilder sb = new StringBuilder();
        for(Character c : s.toCharArray()) {
            sb.append(codeMap.get(c));
        }
        return sb.toString();
    }

    private void buildCodeMap(Node node, String s) {
        if(node == null) {
            return;
        }
        if(node.left == null && node.right == null) {
            codeMap.put(node.c, s);
            return;
        }
        if(node.left != null) {
            buildCodeMap(node.left, s + "0");
        }
        if(node.right != null) {
            buildCodeMap(node.right, s + "1");
        }
    }

    public String decode(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(i < s.length()) {
            Node node = root;
            while(node.left != null || node.right != null) {
                if(i >= s.length()) {
                    throw new RuntimeException("Invalid code: " + s);
                }
                if(s.charAt(i) == '0') {
                    node = node.left;
                } else if(s.charAt(i) == '1') {
                    node = node.right;
                }
                i++;
            }
            sb.append(node.c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        HuffmanEncoding huffman = new HuffmanEncoding();
        String s = huffman.encode("Hello from Bocong");
        System.out.println(s);
        String decode = huffman.decode(s);
        System.out.println(decode);

        String s1 = huffman.encode("A");
        System.out.println(s1);
        String decode1 = huffman.decode(s1);
        System.out.println(decode1);

        String s2 = huffman.encode("ABBBCCCCCCCCDDDDDDEE");
        System.out.println(s2);
        assert s2.equals("11101101101100000000010101010101011111111");
        String decode2 = huffman.decode(s2);
        System.out.println(decode2);


    }
}
