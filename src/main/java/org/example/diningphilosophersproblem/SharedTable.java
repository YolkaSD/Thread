package org.example.diningphilosophersproblem;
import java.util.ArrayList;
import java.util.List;

public class SharedTable {
    private final List<Philosopher> positions = new ArrayList<>();
    private List<Boolean> forks = new ArrayList<>();
    public void add(Philosopher philosopher) throws InterruptedException {
        synchronized (this) {
            positions.add(philosopher);
            forks.add(true);
            System.out.println("Сел за стол: " + Thread.currentThread().getName());
        }
    }

    private void decFork(int pos) {
        forks.set(pos, false);
    }

    private void incFork(int posLeft, int posRight) {
        forks.set(posLeft, true);
        forks.set(posRight, true);
    }

    public void takeFork(Philosopher philosopher) {
        if (Thread.currentThread().getName().equals("July")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int leftHand = positions.indexOf(philosopher);
        int rightHand;
        if (leftHand == 0) {
            rightHand = positions.size() - 1;
        } else {
            rightHand = leftHand - 1;
        }
        if (forks.get(leftHand)) {
            decFork(leftHand);
            philosopher.takeInLeftHand();
            if (forks.get(rightHand)) {
                decFork(rightHand);
                philosopher.takeInRightHand();
            }
        }

    }

    public void putFork(Philosopher philosopher) {
        int leftHand = positions.indexOf(philosopher);
        int rightHand;
        if (leftHand == 0) {
            rightHand = positions.size() - 1;
        } else {
            rightHand = leftHand - 1;
        }
        incFork(leftHand, rightHand);
        philosopher.putDownForks();
    }
}
