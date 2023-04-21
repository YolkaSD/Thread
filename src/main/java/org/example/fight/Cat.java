package org.example.fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cat implements Runnable{
    // Статический контейнер всех созданных котов
    // Класс CopyOnWriteArrayList - тот же ArrayList, только потокобезопасный
    public static final List<Cat> cats = new CopyOnWriteArrayList<>();

    // Имя и количество жизней
    private String name;
    private volatile int life;
    // Личный поток
    private Thread thread;

    // Конструктор: задаем параметры и добавляем в статический список
    public Cat(String name, int life, String threadName) {
        this.name = name;
        this.life = life;
        Cat.cats.add(this);
        thread = new Thread(this, threadName); //Создаем поток этого кота и передаем ему ссылку на себя

        System.out.printf("Кот %s создан. HP: %d%n", this.name, this.life);
    }

    // Атака. Принимает Текущего кота и кота-противника. Метод синхронизирован
    public static synchronized void attack(Cat thisCat, Cat enemyCat) {
        // Дополнительная проверка жизней - во избежание конфликта (У кота может не быть жизней)
        if (thisCat.getLife() <= 0 ) return;

        // Если противник имеет жизни
        if (enemyCat.getLife() > 0) {
            // Отнимает жизнь противника
            enemyCat.decrementLife();
            System.out.printf("Кот %s атаковал кота %s. Жизни %<s: %d \n", thisCat.getName(), enemyCat.getName(), enemyCat.getLife());

            // Если противник не имеет жизней
            if (enemyCat.getLife() <= 0) {
                //Удаляем противника из списка котов
                Cat.cats.remove(enemyCat);

                System.out.printf("Кот %s покидает бой. \n", enemyCat.getName());
                System.out.printf("Оставшиеся коты: %s \n", Cat.cats);
                System.out.printf("%s завершает свою работу. \n", enemyCat.getThread());

                // interrupt() - прервать работу треда
                enemyCat.getThread().interrupt();
            }
        }
    }

    public int getLife() {
        return life;
    }

    public String getName() {
        return name;
    }

    public Thread getThread() {
        return thread;
    }

    public synchronized void decrementLife() {
        life--;
    }

    @Override
    public String toString() {
        return name;
    }

    // Возвращает произвольный объект Cat из cats кроме самого себя
    private Cat getRandomEnemyCat(Cat deleteThisCat) {
        // Создаем лист-копию из основного листа cats
        List<Cat> copyCats = new ArrayList<>(Cat.cats);
        // Удаляем текущего кота, чтобы он не выпал в качестве противника
        copyCats.remove(deleteThisCat);
        // Возвращаем произвольного кота из оставшихся с помощью класса util.java.Random
        return copyCats.get(new Random().nextInt(copyCats.size()));
    }

    @Override
    public void run() {
        System.out.printf("Кот %s идет в бой. \n", this.name);

        // Пока котов больше чем 1
        while (Cat.cats.size() > 1) {
            Cat.attack(this, getRandomEnemyCat(this));
        }
    }
}
