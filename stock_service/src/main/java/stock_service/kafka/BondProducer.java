package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import stock_service.config.TopicsProperties;
import stock_service.entity.MyBond;
import stock_service.entity.MyShare;
import stock_service.service.BondService;
import stock_service.service.ShareService;

@Component
@RequiredArgsConstructor
public class BondProducer {

    private final BondService service;
    private final KafkaTemplate<String, MyBond> kafkaTemplate;
    private final TopicsProperties properties;

    public void sendResponse(String requestedShare) {

        MyBond result = service.getInfo(requestedShare);

        kafkaTemplate.send(properties.getBond(), result);
    }
}
