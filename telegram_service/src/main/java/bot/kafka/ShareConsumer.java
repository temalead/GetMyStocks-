package bot.kafka;


import bot.entity.MyShare;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShareConsumer {

    private final ObjectMapper mapper;


    @KafkaListener(topics = "share.security")
    @SneakyThrows
    public void getShareInfoFromKafka(String message) {
        log.info("Got share {}", message);
        mapper.readValue(message, MyShare.class);

    }
}
