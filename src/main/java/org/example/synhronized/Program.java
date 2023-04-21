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


    }
}

class CommonResources {
    private int x = 1;

    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
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
            System.out.printf("%s %d \n", Thread.currentThread().getName(), res.getX());
            res.incrementX();
        }

    }
}
