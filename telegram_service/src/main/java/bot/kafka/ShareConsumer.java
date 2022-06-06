package bot.kafka;


import bot.entity.MyShare;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShareConsumer {

    private final ObjectMapper mapper;


    @KafkaListener(topics = "share.security")
    @SneakyThrows
    public MyShare getShareInfoFromKafka(String message) {
        log.info("Got share {}", message);
        MyShare result = mapper.readValue(message, MyShare.class);

        return result;
    }
}
