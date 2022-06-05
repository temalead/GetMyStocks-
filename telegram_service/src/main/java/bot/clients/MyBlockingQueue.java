package bot.clients;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<E> {
    private Queue<E> queue;
    private int maxSize = 16;


    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull = lock.newCondition();

    private final Condition notEmpty = lock.newCondition();

    public MyBlockingQueue(int size) {
        this.maxSize = size;
        queue = new LinkedList<>();
    }

    public MyBlockingQueue() {
    }

    @SneakyThrows
    public void put(E e) {
        lock.lock();
        try {
            while (queue.size() == maxSize) {
                notFull.await();
            }
            queue.add(e);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public E take() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await();
            }
            E order = queue.remove();
            notFull.signalAll();
            return order;
        } finally {
            lock.unlock();
        }
    }
}
