package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import stock_service.config.TopicsProperties;
import stock_service.entity.MyBond;
import stock_service.repository.BondRepository;
import stock_service.service.BondMessageCreator;
import stock_service.service.BondService;

@Component
@RequiredArgsConstructor
public class BondProducer {

    private final BondService service;
    private final BondRepository repository;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final BondMessageCreator creator;

    private final TopicsProperties topics;

    @SneakyThrows
    public void sendResponse(String requestedBond) {

        MyBond result = service.getInfo(requestedBond);

        String message = creator.createMessage(result);

        repository.save(result);

        kafkaTemplate.send(topics.getBond_res(), message);
    }
}
