package org.example.synchronizedcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        NameList nameList = new NameList();
        nameList.add("fist");

        class MyThread extends Thread {
            @Override
            public void run() {
                System.out.println(nameList.remove());
            }
        }
        MyThread myThread1 = new MyThread();
        myThread1.setName("one");
        myThread1.start();
        new MyThread().start();
    }

    static class NameList {
        private final List<String> list = Collections.synchronizedList(new ArrayList<>());

        public void add(String name) {
            list.add(name);
        }

        public synchronized String remove() {
            if (list.size() > 0) {
                if (Thread.currentThread().getName().equals("one")) {
                    Thread.yield();
                }
                return list.remove(0);
            }

            return null;
        }
    }
}
