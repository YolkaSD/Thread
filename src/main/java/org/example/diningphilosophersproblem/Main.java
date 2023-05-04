package org.example.diningphilosophersproblem;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SharedTable sharedTable = new SharedTable();

        final List<Philosopher> philosophers = List.of(
                new Philosopher("Jenna", sharedTable),
                new Philosopher("Lena", sharedTable),
                new Philosopher("Nasty", sharedTable),
                new Philosopher("Ira", sharedTable),
                new Philosopher("July", sharedTable)
        );

        philosophers.forEach(philosopher -> new Thread(philosopher, philosopher.getName()).start());

    }

}
