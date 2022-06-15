package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import stock_service.config.TopicsProperties;
import stock_service.service.BondMessageCreator;

@Component
@RequiredArgsConstructor
public class BondProducer {

    private final BondMessageCreator creator;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final TopicsProperties topics;

    @SneakyThrows
    public void sendResponse(String requestedBond) {

        String message = creator.createMessage(requestedBond);

        kafkaTemplate.send(topics.getBond_res(), message);
    }
}
