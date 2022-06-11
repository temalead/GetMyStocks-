package bot.kafka;


import bot.entity.MyShare;
import bot.repository.ShareRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShareConsumer implements Consumer {
    private final ShareRepository repository;

    @Override
    public MyShare getSecurityFromKafka(String ticker) {

        return repository.findById(ticker).orElseThrow();

    }
}
