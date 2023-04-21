package org.example.fightcats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatFightConsole {
    public static void main(String[] args) {
        //Title
        System.out.println("Cat Fight Console");

        // Создаем контейнер с котами
        List<Cat> catThread = new ArrayList<>();
        // Жизни котов
        int life = 9;
        // Создаём и настраиваем классы-потоки котов, добавляя их в контейнер
        Collections.addAll(catThread,
                new Cat("Tom", life, "Thread Tom"),
                new Cat("Cleopatra", life, "Thread Cleopatra"),
                new Cat("Dupli", life, "Thread Dupli"),
                new Cat("Toodles", life, "Thread Toodles")
        );

        // Запускаем котов
        for (Cat cat : catThread) {
            cat.getThread().start();
        }

        // Ждем, пока завершатся все, кроме главного
        for (Cat cat : catThread) {
            try{
                // Поток, который вызвал метод join(), приостанавливается на этой строчке
                cat.getThread().join();
                // Пока поток, на котором вызван метод, не завершит работу, Main ждёт остальных
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Последний выживший — первый элемент cats
        System.out.printf("Кот-победитель: %s!!!", Cat.cats.get(0));
    }
}
