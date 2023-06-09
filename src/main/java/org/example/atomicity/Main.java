package org.example.atomicity;
import java.util.concurrent.atomic.*;

public class Main {
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 10_000; j++) {
            new MyThread().start();
        }
        Thread.sleep(2000);
        System.out.println(atomicInteger.get());

    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            atomicInteger.incrementAndGet();
        }
    }
}
