package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.entity.Request;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String TOPIC = "req.topic";
    private final ShareProducer shareProducer;


    @KafkaListener(topics = TOPIC)
    public void getRequestFromTelegram(Request request) {

        String requestedShare = request.getMessage().getText();

        shareProducer.sendResponse(requestedShare);

    }
}
