package bot.kafka;


import bot.entity.MyBond;
import bot.entity.Security;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BondConsumer implements Consumer {
    private final ObjectMapper mapper;


    @KafkaListener(id="bond",topics = "bond.topic")
    @SneakyThrows
    @Override
    public MyBond getSecurityFromKafka(String message) {
        log.info("Got Bond {}", message);

        return mapper.readValue(message, MyBond.class);
    }
}
