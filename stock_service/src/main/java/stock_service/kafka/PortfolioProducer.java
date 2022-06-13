package stock_service.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock_service.entity.Portfolio;
import stock_service.entity.User;
import stock_service.utils.PortfolioService;

@Service
@RequiredArgsConstructor
public class PortfolioProducer {

    private final PortfolioService service;


    public void sendResponse(String message, User user) {
        Portfolio portfolio = service.getInfo(message, user);



    }
}
