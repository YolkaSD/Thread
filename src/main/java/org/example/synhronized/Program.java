package org.example.synhronized;

public class Program {
    public static void main(String[] args) throws InterruptedException {

        CommonResources commonResources = new CommonResources();

        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new CountThread(commonResources));
            t.setName("Thread " + i);
            t.start();
            //t.join();
        }

        Thread.sleep(2000);
        System.out.println(commonResources.getX());

    }
}

class CommonResources {
    private int x = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public synchronized void incrementX() {
        int x = this.x;
        x++;
        this.x = x;
    }
}

class CountThread implements Runnable {

    final CommonResources res;

    public CountThread(CommonResources res) {
        this.res = res;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            if (Thread.currentThread().getName().equals("Thread 5") || Thread.currentThread().getName().equals("Thread 4")) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            synchronized (res) {
                res.setX(res.getX() + 1);
                System.out.printf("%s %d \n", Thread.currentThread().getName(), res.getX());
            }

        }

    }
}
