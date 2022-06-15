package bot.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShareResponseConsumer implements Callable<String> {

    private final String SHARE_RES_TOPIC = "share_res.topic";
    private String message;

    @SneakyThrows
    @KafkaListener(id = "share_res", topics = SHARE_RES_TOPIC)
    public void getShareResponse(String message) {
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        while (this.message == null) {
            Thread.sleep(10);
        }

        return this.message;
    }

    public void shutdown(){
        this.message=null;
    }
}

