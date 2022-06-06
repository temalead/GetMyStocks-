package stock_service.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.config.TopicsProperties;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String TOPIC = "req.topic";

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = TOPIC)
    public void getRequestFromTelegram() {

    }

}
