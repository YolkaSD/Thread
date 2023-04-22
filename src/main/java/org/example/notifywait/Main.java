package org.example.notifywait;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RunnableB runnableB = new RunnableB();
        Thread threadB = new Thread(runnableB);
        threadB.start();
        synchronized (threadB) {
            threadB.wait();
        }

        System.out.println(runnableB.total);

    }
}

class RunnableB implements Runnable {
    int total;

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                total += i;
            }
            notify();
        }

    }
}
