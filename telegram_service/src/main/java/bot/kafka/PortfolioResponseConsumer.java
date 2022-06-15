package bot.kafka;

import bot.service.sender.PortfolioSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioResponseConsumer extends Consumer implements Callable<String> {
    private final String PORTFOLIO_TOPIC = "portfolio_res.topic";

    @KafkaListener(id = "portfolio", topics = PORTFOLIO_TOPIC)
    @SneakyThrows
    public void getPortfolioFromKafka(String message) {
        this.message=message;
    }


    @Override
    public String call() throws Exception {
        while (this.message == null) {
            Thread.sleep(10);
        }

        return this.message;
    }
}
