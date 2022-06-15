package stock_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import stock_service.config.TopicsProperties;
import stock_service.entity.MyShare;
import stock_service.repository.ShareRepository;
import stock_service.service.ShareMessageCreator;
import stock_service.service.ShareService;

@Service
@RequiredArgsConstructor
public class ShareProducer {

    private final ShareService service;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties properties;

    private final ShareMessageCreator creator;

    private final ShareRepository repository;

    @SneakyThrows
    public void sendResponse(String requestedShare) {

        MyShare resultShare = service.getInfo(requestedShare);

        String message = creator.createMessage(resultShare);

        repository.save(resultShare);

        kafkaTemplate.send(properties.getShare_res(), message);
    }
}
