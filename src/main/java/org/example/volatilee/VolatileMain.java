package org.example.volatilee;

public class VolatileMain {
    volatile static int i = 0;

    public static void main(String[] args) {

        new MyThreadWrite().start();
        new MyThreadRead().start();

    }

    static class MyThreadWrite extends Thread {
        @Override
        public void run() {
            while (i < 5) {
                System.out.println("increment 'i' to " + (++i));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class MyThreadRead extends Thread {
        @Override
        public void run() {
            int localVar = i;
            while (localVar < 5) {
                if (localVar != i) {
                    System.out.println("new value of 'i' is " + i);
                    localVar = i;
                }
            }
        }
    }
}
