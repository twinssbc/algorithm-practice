public class BloomFilter {
    private int size;
    private int hashCount;
    private boolean[] bitArray;
    private final int[] seeds = new int[]{3, 13, 31, 71, 97, 151};

    public BloomFilter(int n, double fpp) {
        size = getSize(n, fpp);
        hashCount = getHashCount(size, n);
        System.out.println("Size: " + size);
        System.out.println("HashCount:" + hashCount);
        bitArray = new boolean[size];
    }

    private int getSize(int n, double p) {
        // P = (1-[1-1/m]^kn)^k = (1-e^(-kn/m)^k
        // m = -n*lnP/(ln2)^2
        // k = m/n * ln2
        double m = -n * Math.log(p) / (Math.log(2) * Math.log(2));
        return (int) m;
    }

    private int getHashCount(int m, int n) {
        return (int) (m * Math.log(2) / n);
    }

    public void put(Object object) {
        for(int i = 0; i < hashCount; i++) {
            int hash = hash(object, seeds[i]);
            bitArray[hash] = true;
        }
    }

    public boolean check(Object object) {
        for(int i = 0; i < hashCount; i++) {
            int hash = hash(object, seeds[i]);
            if(!bitArray[hash]) {
                return false;
            }
        }
        return true;
    }

    private int hash(Object value, int seed) {
        return (value == null) ? 0 : (value.hashCode() * seed) & (size -1);
    }

    public static void main(String[] args) {
        BloomFilter filter = new BloomFilter(10, 0.01);
        String[] input = new String[] {"Hello", "two", "sigma", "from", "bocong", "test", "foo", "bar"};
        for(String s : input) {
            filter.put(s);
        }

        for(String s: input) {
            System.out.println(filter.check(s));
        }
        System.out.println(filter.check("john"));
    }
}
