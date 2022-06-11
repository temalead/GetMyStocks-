package stock_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties properties;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void sendResponse(String requestedShare) {

        MyBond result = service.getInfo(requestedShare);
        String value = mapper.writeValueAsString(result);

        kafkaTemplate.send(properties.getBond(), value);
    }
}
