package org.example;

public class CreateThreadMain {
    public static void main(String[] args) {
        SomeThingOne someThingOne = new SomeThingOne();
        SomeThingTwo someThingTwo = new SomeThingTwo();
        Thread thread1 = new Thread(someThingOne);
        Thread thread3 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " Hello from lambda Thread"));
        thread1.start();
        someThingTwo.start();
        thread3.start();
        System.out.println(Thread.currentThread().getName() + " Hello from main Thread");
    }
}

class SomeThingOne implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Hello from Runnable");
    }
}

class SomeThingTwo extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Hello from Thread");
    }

}
