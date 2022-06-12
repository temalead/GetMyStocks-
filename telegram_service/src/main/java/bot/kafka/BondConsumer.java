package bot.kafka;


import bot.entity.MyBond;
import bot.entity.Security;
import bot.repository.BondRepository;
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
    private final BondRepository repository;

    @SneakyThrows
    @Override
    public MyBond getSecurityFromKafka(String message) {
        return repository.findById(message).orElseThrow();

    }
}
