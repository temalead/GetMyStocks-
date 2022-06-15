package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import stock_service.config.TopicsProperties;
import stock_service.service.ShareMessageCreator;

@Service
@RequiredArgsConstructor
public class ShareProducer {

    private final ShareMessageCreator creator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties properties;

    @SneakyThrows
    public void sendResponse(String requestedShare) {

        String message = creator.createMessage(requestedShare);

        kafkaTemplate.send(properties.getShare_res(), message);
    }
}
