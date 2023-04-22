package org.example.notifywait;

import java.util.Random;

public class PictureExample {
    public static void main(String[] args) {
        Picture picture = new Picture();
        Artist artist = new Artist(picture);
        Printer printer = new Printer(picture);

        Thread artistThread = new Thread(artist);
        artistThread.setName("Artist");
        artistThread.start();

        Thread printerThread = new Thread(printer);
        printerThread.setName("Printer");
        printerThread.start();


    }

}

class Picture {
    private volatile int picture = 0;

    public synchronized void addPic() throws InterruptedException {
        while (picture > 3) {
            System.out.println("Отдыхаю!");
            wait();
        }
        Random random = new Random();
        int m;
        m = random.nextInt(1000) + 500;
        System.out.println("Начинаю рисовать...");
        Thread.sleep(m);
        m = random.nextInt(1000) + 500;
        System.out.println("Продолжаю...");
        Thread.sleep(m);
        this.picture++;
        System.out.println("На столе картин: " + this.picture);
        notify();
    }

    public synchronized void print() {
        while (picture < 1) {
            try {
                System.out.println("Жду картину!");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        picture--;
        System.out.println("Распечатал!");
        notify();
    }
}

class Printer implements Runnable {
    private final Picture picture;

    public Printer(Picture picture) {
        this.picture = picture;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            picture.print();
        }
    }

}

class Artist implements Runnable {
    private final Picture picture;

    public Artist(Picture picture) {
        this.picture = picture;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 15; i++) {
                picture.addPic();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}