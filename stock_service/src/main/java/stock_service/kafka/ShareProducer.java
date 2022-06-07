package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import stock_service.config.TopicsProperties;
import stock_service.entity.MyShare;
import stock_service.service.ShareService;

@Service
@RequiredArgsConstructor
public class ShareProducer {

    private final ShareService service;
    private final KafkaTemplate<String, MyShare> kafkaTemplate;
    private final TopicsProperties properties;

    public void sendResponse(String requestedShare) {

        MyShare resultShare = service.getInfo(requestedShare);

        kafkaTemplate.send(properties.getShare(), resultShare);
    }
}
