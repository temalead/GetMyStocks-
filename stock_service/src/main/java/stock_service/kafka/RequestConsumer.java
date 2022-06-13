package stock_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.entity.Request;
import stock_service.entity.User;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String SHARE_TOPIC = "share.topic";
    private final String BOND_TOPIC = "bond.topic";
    private final String PORTFOLIO_TOPIC = "portfolio.topic";
    private final ShareProducer shareProducer;
    private final BondProducer bondProducer;
    private final ObjectMapper mapper;


    @SneakyThrows
    @KafkaListener(id = "share", topics = SHARE_TOPIC)
    public void getShareRequest(String message) {
        Request request = mapper.readValue(message, Request.class);


        System.out.println("Got request from share topic");
        String requestedShare = request.getMessage();

        shareProducer.sendResponse(requestedShare);

    }

    @KafkaListener(id = "bond", topics = BOND_TOPIC)
    public void getBondRequest(Request request) {

        String requestedShare = request.getMessage();

        bondProducer.sendResponse(requestedShare);

    }

    @KafkaListener(id = "portfolio", topics = PORTFOLIO_TOPIC)
    public void getPortfolioRequest(Request request) {
        String message = request.getMessage();
        User user = request.getUser();


        //portfolioProducer.sendResponse(message, user);

    }
}
