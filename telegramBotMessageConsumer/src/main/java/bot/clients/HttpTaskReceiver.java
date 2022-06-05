package bot.clients;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpTaskReceiver {


    ExecutorService threadPool = Executors.newFixedThreadPool(4);

    @SneakyThrows
    private Set<Integer> getPricesOfOrderFromSites(int productId) {

        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());

        CountDownLatch latch = new CountDownLatch(3);

        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Order("www1", 1, prices));
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Order("www2", 1, prices));
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Order("www3", 1, prices));

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);

        allTasks.get(3,TimeUnit.SECONDS);
        return prices;
    }


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Order implements Runnable {
        private String url;
        private int productId;
        private Set<Integer> prices;

        @Override
        public void run() {
            int price = 0;
            prices.add(price);
        }
    }
}
