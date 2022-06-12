package stock_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.entity.MyShare;
import stock_service.entity.Request;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String SHARE_TOPIC = "share.topic";
    private final String BOND_TOPIC = "bond.topic";
    private final ShareProducer shareProducer;
    private final BondProducer bondProducer;
    private final ObjectMapper mapper;


    @SneakyThrows
    @KafkaListener(id = "share", topics = SHARE_TOPIC)
    public void getShareRequest(String message) {
        String requestedShare = getRequestedTicker(message);
        shareProducer.sendResponse(requestedShare);

    }

    @KafkaListener(id = "bond", topics = BOND_TOPIC)
    public void getBondRequest(String message) {
        String requestedBond = getRequestedTicker(message);
        bondProducer.sendResponse(requestedBond);

    }

    @SneakyThrows
    private String getRequestedTicker(String message) {
        Request request = mapper.readValue(message, Request.class);
        return request.getMessage().getText();
    }
}
