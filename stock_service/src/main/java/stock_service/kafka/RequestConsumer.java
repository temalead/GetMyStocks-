package stock_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import stock_service.entity.Portfolio;
import stock_service.entity.Request;
import stock_service.entity.User;

import java.util.Locale;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestConsumer {


    private final String SHARE_TOPIC = "share.topic";
    private final String BOND_TOPIC = "bond.topic";
    private final String PORTFOLIO_TOPIC = "portfolio.topic";
    private final ShareProducer shareProducer;
    private final BondProducer bondProducer;

    private final PortfolioProducer portfolioProducer;
    private final ObjectMapper mapper;


    @SneakyThrows
    @KafkaListener(id = "share", topics = SHARE_TOPIC)
    public void getShareRequest(String message) {
        Request request = mapper.readValue(message, Request.class);


        log.info("Got share request from {} topic", request.getMessage());
        String requestedShare = request.getMessage().toUpperCase(Locale.ROOT);

        shareProducer.sendResponse(requestedShare);

    }

    @KafkaListener(id = "bond", topics = BOND_TOPIC)
    @SneakyThrows
    public void getBondRequest(String message) {
        Request request = mapper.readValue(message, Request.class);
        log.info("Got bond request from {} topic", request.getMessage());

        String requestedShare = request.getMessage();

        bondProducer.sendResponse(requestedShare);

    }

    @SneakyThrows
    @KafkaListener(id = "portfolio", topics = PORTFOLIO_TOPIC)
    public void getPortfolioRequest(String message) {
        Request request = mapper.readValue(message, Request.class);
        String text = request.getMessage();
        User user = request.getUser();


        portfolioProducer.sendResponse(text, user);

    }
}
