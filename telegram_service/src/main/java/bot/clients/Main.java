package bot.clients;

import bot.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(10);

        List<Future<Integer>> allFutures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = service.submit(new Task());
            allFutures.add(future);
        }
        System.out.println(allFutures);


        allFutures.forEach((x) -> {
            try {
                x.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(allFutures);
        System.out.println("Thread: " + Thread.currentThread().getName());

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {

            CompletableFuture.supplyAsync(Main::getOrder)
                    .thenApply(Main::enrich)
                    .thenAccept(Main::sendEmail);
        }

        System.out.println(System.currentTimeMillis() - start);

    }

    private static Order getOrder() {
        return Order.builder()
                .id(1)
                .cost(10).build();
    }

    private static Order enrich(Order order) {
        order.setCost(100);
        return order;
    }

    private static void sendEmail(Order order) {
        System.out.printf("Order with id %s was received", order.getId());
        System.out.println();
    }

}

class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return 42;
    }

}
