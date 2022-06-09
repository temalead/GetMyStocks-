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
public class ShareConsumer implements Consumer{

    private final ObjectMapper mapper;


    @KafkaListener(id="share",topics = "share.topic")
    @SneakyThrows
    @Override
    public MyShare getSecurityFromKafka(String message) {
        log.info("Got share {}", message);

        return mapper.readValue(message, MyShare.class);
    }
}
