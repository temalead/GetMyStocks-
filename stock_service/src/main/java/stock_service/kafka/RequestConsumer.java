package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.entity.Request;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String SHARE_TOPIC = "share.topic";
    private final String BOND_TOPIC = "bond.topic";
    private final ShareProducer shareProducer;


    @KafkaListener(topics = SHARE_TOPIC)
    public void getShareRequest(Request request) {

        String requestedShare = request.getMessage().getText();

        shareProducer.sendResponse(requestedShare);

    }

    @KafkaListener(topics = BOND_TOPIC)
    public void getBondRequest(Request request) {

        String requestedShare = request.getMessage().getText();

        shareProducer.sendResponse(requestedShare);

    }
}
