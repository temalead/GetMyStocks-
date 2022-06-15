package bot.utils;

import bot.kafka.Consumer;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaResultReceiver {


    @SneakyThrows
    public static String getResult(Consumer consumer) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        String result = service.submit(consumer).get();
        consumer.shutdown();

        return result;
    }
}
