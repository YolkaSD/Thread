package org.example.lifecycle;

public class ThreadLifeCycle {
    public static void main(String[] args) {
        // При создании объект имеет состояние NEW
        Thread myThread = new Thread(new MyThread());
        System.out.println(myThread.getState());

        // Нить запускается и переходит в состояние RUNNABLE
        myThread.start();
        System.out.println(myThread.getState());

        // main переходит в состояние WAITING
        try {
            myThread.join(); // main на этой строчке приостановится,
            // чтобы подождать, пока myThread завершит свою работу в методе run(),
            //и только потом код будет выполняться дальше
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Объект завершил свою работу и получил статус TERMINATED
        System.out.println(myThread.getState());
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {

    }
}
