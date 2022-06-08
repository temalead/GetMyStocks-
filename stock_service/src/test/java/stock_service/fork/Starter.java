package stock_service.fork;

import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.IntStream;

public class Starter {
    public static void main(String[] args) {
        final int MAX = 16;
        List<Integer> list = IntStream.range(1, MAX + 1).boxed().toList();

        Spliterator<Integer> sp1 = list.spliterator();
        Spliterator<Integer> sp2 = sp1.trySplit();
        sp1.forEachRemaining(System.out::println);
        System.out.println("---");
        sp2.forEachRemaining(System.out::println);



        list.parallelStream()
                .forEach(i -> {
                    System.out.println("Start: " + Thread.currentThread().getId() + ":" + i);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Finish: " + Thread.currentThread().getId() + ":" + i);
                });

    }
}

