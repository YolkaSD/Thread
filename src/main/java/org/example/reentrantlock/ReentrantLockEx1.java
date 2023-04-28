package org.example.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockEx1 {
    public static void main(String[] args) throws InterruptedException {
        Resources resources = new Resources();
        resources.i = 5;

        MyThread myThread1 = new MyThread();
        myThread1.setName("One");
        MyThread myThread2 = new MyThread();
        myThread1.resources = resources;
        myThread2.resources = resources;
        myThread1.start();
        myThread2.start();
        myThread1.join();
        myThread2.join();
        System.out.println(resources.i);
    }

    static class MyThread extends Thread {
        Resources resources;
        @Override
        public void run() {
            resources.changeI();
        }
    }
}

class Resources {
    int i;

    Lock lock = new ReentrantLock();

    void changeI() {
        lock.lock();
        int i = this.i;
        if (Thread.currentThread().getName().equals("One")) {
            Thread.yield();
        }
        i++;
        this.i = i;
        lock.unlock();
    }
}
