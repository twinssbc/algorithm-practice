public class MemoryAllocator {
    private final int[] space;
    private final int totalSize;
    public MemoryAllocator(int totalSize) {
        space = new int[totalSize];
        this.totalSize = totalSize;
        assignHeader(0, totalSize, 0, -1);
    }

    public int malloc(int size) throws Exception {
        System.out.println("malloc: " + size);
        int startIndex = 0;
        while(startIndex < totalSize) {
            if(space[startIndex + 1] == 0 && space[startIndex] >= size + 3) {
                int leftChunkSize = space[startIndex] - size - 3;
                if(leftChunkSize <= 3) {
                    // padding to the allocated chunk
                    size += leftChunkSize;
                }
                int originalNextChuckIndex = startIndex + space[startIndex];
                assignHeader(startIndex, size + 3, 1, space[startIndex + 2]);
                // allocate left avail chunk header
                if(leftChunkSize > 3) {
                    assignHeader(startIndex + size + 3, leftChunkSize, 0, startIndex);
                    if(originalNextChuckIndex < totalSize) {
                        assignHeader(originalNextChuckIndex, space[originalNextChuckIndex], space[originalNextChuckIndex + 1], startIndex + size + 3);
                    }
                }
                return startIndex + 3;
            } else {
                // find next available slot
                startIndex = startIndex + space[startIndex];
            }
        }
        System.out.println("No free space");
        return -1;
    }

    public void free(int ptr) {
        System.out.println("free: " + ptr);
        // do validation check if necessary
        int chunkIndex = ptr - 3;
        int chunkSize = space[chunkIndex];
        int chunkPrev = space[chunkIndex + 2];

        assignHeader(chunkIndex, chunkSize, 0, chunkPrev);
        // merge prev
        if(chunkPrev != -1 && space[chunkPrev + 1] == 0) {
            assignHeader(chunkPrev, space[chunkPrev] + chunkSize, space[chunkPrev+1], space[chunkPrev+2]);
            chunkIndex = chunkPrev;
        }

        int nextChunkIndex = chunkIndex + space[chunkIndex];
        if(nextChunkIndex < totalSize) {
            if(space[nextChunkIndex + 1] == 1) {
                space[nextChunkIndex + 2] = chunkIndex;
            } else {
                // merge next
                int nextNextChunkIndex = nextChunkIndex + space[nextChunkIndex];
                if (nextNextChunkIndex < totalSize) {
                    assignHeader(chunkIndex, space[chunkIndex] + space[nextChunkIndex], space[chunkIndex+1], space[chunkIndex+2]);
                    assignHeader(nextNextChunkIndex, space[nextNextChunkIndex], space[nextNextChunkIndex+1], chunkIndex);
                } else {
                    assignHeader(chunkIndex, space[chunkIndex] + space[nextChunkIndex], space[chunkIndex+1], space[chunkIndex+2]);
                }
            }
        }
    }

    private void assignHeader(int index, int size, int status, int prevAddress) {
        space[index] = size;
        space[index+1] = status;
        space[index+2] = prevAddress;
        System.out.println("Assign Header: [" + index + ", " + size + ", " + status + ", " + prevAddress + "]");
    }

    public static void main(String[] args) {
        try {
            MemoryAllocator allocator = new MemoryAllocator(100);
           int ptr40= allocator.malloc(40);
           assert ptr40 == 3;
           int ptr30 = allocator.malloc(30);
           assert ptr30 == 46;
            allocator.free(ptr40);
            allocator.free(ptr30);
            int ptr10 = allocator.malloc(10);
            assert ptr10== 3;
            int ptr20 = allocator.malloc(20);
            assert ptr20 == 16;
            int ptr50 = allocator.malloc(50);
            assert ptr50 == 39;
            int ptr11 = allocator.malloc(11);
            assert ptr11 == -1;
            int ptr8 = allocator.malloc(8);
            assert ptr8 == 92;
            allocator.free(ptr10);
            allocator.free(ptr50);
            ptr50 = allocator.malloc(50);
            assert ptr50 == 39;
            allocator.free(ptr50);

            allocator.free(ptr20);
            int ptr86 = allocator.malloc(86);
            assert ptr86 == 3;
        } catch(Exception e) {
            System.out.println("Invalid operation, exception: " + e);
        }
    }
}
