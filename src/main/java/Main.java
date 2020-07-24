import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static final int SIZE = 10_000_000;
    static float[] arr = new float[SIZE];

    public static void main(String[] args) throws InterruptedException {

        long duration1 = oneThreadExample();
        long duration2 = twoThreadsExample();
        long duration3 = twoThreadsWithExecutorServiceExample();

        System.out.println("One thread duration: " + duration1);
        System.out.println("Two threads duration: " + duration2 +
                " [faster by " + (duration2 * 100) / duration1 + "%]");
        System.out.println("Two threads with ExecutorService duration: " + duration3 +
                " [faster by " + (duration3 * 100) / duration1 + "%]");
    }

    private static long oneThreadExample() {
        initArrayByOne();

        long time = System.currentTimeMillis();
        calcArrayValues(arr, 0);
        return System.currentTimeMillis() - time;
    }

    private static long twoThreadsExample() throws InterruptedException {
        initArrayByOne();

        int h1 = SIZE / 2;
        int h2 = SIZE - h1;//For arr of odd length

        float[] a1 = new float[h1];
        float[] a2 = new float[h2];

        long time = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h1);
        System.arraycopy(arr, h1, a2, 0, h2);

        Thread t1 = new Thread(() -> calcArrayValues(a1, 0));
        Thread t2 = new Thread(() -> calcArrayValues(a2, h1));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.arraycopy(a1, 0, arr, 0, h1);
        System.arraycopy(a2, 0, arr, h1, h2);

        return System.currentTimeMillis() - time;
    }

    private static long twoThreadsWithExecutorServiceExample() throws InterruptedException {
        initArrayByOne();

        int h1 = SIZE / 2;
        int h2 = SIZE - h1; //For arr of odd length

        float[] a1 = new float[h1];
        float[] a2 = new float[h2];

        long time = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h1);
        System.arraycopy(arr, h1, a2, 0, h2);

        ExecutorService service = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        service.execute(() -> {
            calcArrayValues(a1, 0);
            latch.countDown();
        });
        service.execute(() -> {
            calcArrayValues(a2, h1);
            latch.countDown();
        });

        latch.await();
        service.shutdown();

        System.arraycopy(a1, 0, arr, 0, h1);
        System.arraycopy(a2, 0, arr, h1, h2);

        return System.currentTimeMillis() - time;


    }

    private static void calcArrayValues(float[] a, int startDelta) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (float) (a[i] *
                    Math.sin(0.2f + (float) (i + startDelta) / 5) *
                    Math.cos(0.2f + (float) (i + startDelta) / 5) *
                    Math.cos(0.4f + (float) (i + startDelta) / 2));
        }
    }

    private static void initArrayByOne() {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
    }
}
