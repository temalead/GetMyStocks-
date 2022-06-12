package bot.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShareResponseConsumer {
    private final String SHARE_RES_TOPIC = "share_res.topic";

    //private final ShareRedundant shareRedundant;

    @SneakyThrows
    @KafkaListener(id = "share_res", topics = SHARE_RES_TOPIC)
    public void getShareResponse(String message) {

        //  shareRedundant.setMessage(message);

    }
}

