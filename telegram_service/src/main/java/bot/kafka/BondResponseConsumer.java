package bot.kafka;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
@Slf4j
@RequiredArgsConstructor
public class BondResponseConsumer implements Callable<String> {
    private final String BOND_RES_TOPIC = "bond_res.topic";


    private String message;

    @KafkaListener(id="bond_res",topics = BOND_RES_TOPIC)
    @SneakyThrows
    public void getSecurityFromKafka(String message) {
        this.message=message;
    }

    @Override
    public String call() throws Exception {
        while (this.message == null) {
            Thread.sleep(10);
        }

        return this.message;
    }

    public void shutdown() {
        this.message=null;
    }
}
