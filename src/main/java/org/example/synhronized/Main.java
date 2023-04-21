package org.example.synhronized;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Resource resource = new Resource(5);

        MyThread myClass1 = new MyThread();
        MyThread myClass2 = new MyThread();
        myClass1.setResource(resource);
        myClass2.setResource(resource);

        Thread thread1 = new Thread(myClass1);
        thread1.setName("one");
        Thread thread2 = new Thread(myClass2);

        thread1.start();
        thread2.start();

        Thread.sleep(10);

//        thread1.join();
//        thread2.join();

        System.out.println(resource.getI());

    }
}

class MyThread implements Runnable {
    Resource resource;

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        resource.changeI();
    }
}

class Resource {
    private int i;

    public Resource(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public synchronized void changeI() {
        int i = this.i;
        if (Thread.currentThread().getName().equals("one")) Thread.yield();
        i++;
        this.i = i;
    }
}
