package org.example.diningphilosophersproblem;

public class Philosopher implements Runnable {
    private final String name;
    private final SharedTable sharedTable;
    private boolean rightHand = false;
    private boolean leftHand = false;

    public Philosopher(String name, SharedTable sharedTable) {
        this.name = name;
        this.sharedTable = sharedTable;
    }
    public String getName() {
        return name;
    }

    private void sitAtTheTable() throws InterruptedException {
        sharedTable.add(this);
    }

    public void takeInLeftHand() {
        leftHand = true;
        System.out.println("Взял вилку в левую руку: " + Thread.currentThread().getName());
    }

    public void takeInRightHand() {
        rightHand = true;
        System.out.println("Взял вилку в правую руку: " + Thread.currentThread().getName());
    }

    private void eating() throws InterruptedException {
        System.out.println("Приступил кушать: " + this.getName());
        System.out.println("Закончил обедать: " + this.getName());
        sharedTable.putFork(this);
    }

    public void putDownForks() {
        leftHand = false;
        rightHand = false;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Начал свою работу поток: " + Thread.currentThread().getName());
        try {
            sitAtTheTable();
            while (true) {
                sharedTable.takeFork(this);
                if (leftHand && rightHand) {
                    eating();

                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
