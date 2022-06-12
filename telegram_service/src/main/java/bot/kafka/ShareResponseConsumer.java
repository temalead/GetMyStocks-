package bot.kafka;

import bot.service.sender.ShareRedundant;
import bot.service.sender.ShareSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShareResponseConsumer {
    private final String SHARE_RES_TOPIC = "share_res.topic";

    private final ShareRedundant shareRedundant;

    @SneakyThrows
    @KafkaListener(id = "share_res", topics = SHARE_RES_TOPIC)
    public void getShareResponse(String message) {

        shareRedundant.setMessage(message);

    }
}

