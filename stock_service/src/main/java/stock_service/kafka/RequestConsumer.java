package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.entity.Request;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String TOPIC = "req.topic";


    @KafkaListener(topics = TOPIC)
    public String getRequestFromTelegram(Request request) {

        return request.getMessage().getText();

    }
}
