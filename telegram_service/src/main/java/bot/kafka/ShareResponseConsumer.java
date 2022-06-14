package bot.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShareResponseConsumer{
    private final String SHARE_RES_TOPIC = "share_res.topic";

    private final ShareRespondent respondent;

    @SneakyThrows
    @KafkaListener(id = "share_res", topics = SHARE_RES_TOPIC)
    public String getShareResponse(String message) {

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(respondent);
        respondent.sendMessageFromKafka(message);
        return message;
    }
}

