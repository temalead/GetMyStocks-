package stock_service.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import stock_service.config.TopicsProperties;
import stock_service.entity.User;
import stock_service.service.PortfolioMessageCreator;

@Service
@RequiredArgsConstructor
public class PortfolioProducer {

    private final PortfolioMessageCreator creator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties properties;

    public void sendResponse(String request, User user) {
        String message = creator.createMessage(request, user);


        kafkaTemplate.send(properties.getPortfolio_res(), message);

    }
}
