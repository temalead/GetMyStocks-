package stock_service.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock_service.entity.Portfolio;
import stock_service.entity.User;
import stock_service.service.PortfolioMessageCreator;
import stock_service.utils.PortfolioService;

@Service
@RequiredArgsConstructor
public class PortfolioProducer {

    private final PortfolioMessageCreator creator;


    public void sendResponse(String request, User user) {
        String message = creator.createMessage(request, user);


    }
}
