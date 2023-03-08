import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RateLimiter {
    private int limit;
    private int refillAmount;
    private int refillPeriod;
    private ArrayBlockingQueue<String> arrayBlockingQueue;
    private static final String TOKEN = "token";

    public RateLimiter(int limit, int refillAmount, int refillPeriod) {
        this.limit = limit;
        this.refillAmount = refillAmount;
        this.refillPeriod = refillPeriod;
        arrayBlockingQueue = new ArrayBlockingQueue<>(limit, true);
    }

    public void start() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            addToken();
        }, 0, refillPeriod, TimeUnit.SECONDS);
    }

    private void addToken() {
        for (int i = 0; i < refillAmount; i++) {
            arrayBlockingQueue.offer(TOKEN);
        }
    }

    public boolean tryAcquire() {
        return arrayBlockingQueue.poll() != null;
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter(2, 2, 5);
        rateLimiter.start();
        ExecutorService es = Executors.newFixedThreadPool(4);
        for(int i = 0; i < 4; i++) {
            es.submit(() -> {
                while(true) {
                    boolean acquiredToken = rateLimiter.tryAcquire();
                    ZonedDateTime now = ZonedDateTime.now();
                    if(acquiredToken) {
                        System.out.println(now + "[" + Thread.currentThread().getName() + "], token: " + acquiredToken);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
