package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import stock_service.config.TopicsProperties;
import stock_service.entity.MyShare;
import stock_service.repository.ShareRepository;
import stock_service.service.ShareMessageCreator;
import stock_service.service.ShareService;

@Component
@RequiredArgsConstructor
public class ShareProducer {

    private final ShareService service;
    private final ShareMessageCreator creator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties topics;

    @SneakyThrows
    public void sendResponse(String requestedShare) {

        MyShare resultShare = service.getInfo(requestedShare);
        String result = creator.createMessage(resultShare);


        kafkaTemplate.send(topics.getShare_res(), result);
    }
}
