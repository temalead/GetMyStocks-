package stock_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock_service.entity.Asset;
import stock_service.entity.Portfolio;
import stock_service.entity.User;
import stock_service.entity.dto.SecurityDto;
import stock_service.exception.PortfolioCompositionValidationException;
import stock_service.utils.FractionOccupiedPortfolioCalculator;
import stock_service.utils.PortfolioService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioMessageCreator {

    private final PortfolioService service;

    private final FractionOccupiedPortfolioCalculator calculator;

    public String createMessage(String request, User user) {
        Portfolio info;
        try {
            info = service.getInfo(request, user);
        } catch (PortfolioCompositionValidationException ex) {
            return ex.getMessage();
        }

        return null;
    }


    public String createSecurityMessage(Portfolio portfolio, User user) {

        if (portfolio == null) {
            return "null";
        }

        List<SecurityDto> list = portfolio.getSecurities();


        StringBuilder stringBuilder = new StringBuilder();


        for (SecurityDto security : list) {
            stringBuilder.append(createSecurityMessage(security));
            BigDecimal fraction = calculator.calculateOccupiedFractionOfSecurityByUser(security, user);
            stringBuilder.append("Fraction of Portfolio:").append(fraction).append("%").append("\n");
            stringBuilder.append("\n");
        }

        String shareFraction = calculator.calculateFraction(portfolio, Asset.SHARE);
        String bondFraction = calculator.calculateFraction(portfolio, Asset.BOND);

        stringBuilder.append("Share fraction: ").append(shareFraction).append("\n");
        stringBuilder.append("Bond fraction: ").append(bondFraction).append("\n");

        return stringBuilder.toString();

    }

    public String createSecurityMessage(SecurityDto security) {
        StringBuilder stringBuilder = new StringBuilder();

        BigDecimal price = security.getPrice();
        BigDecimal lot = security.getLot();
        String name = security.getName();
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Quantity: ").append(lot).append("\n");

        return stringBuilder.toString();
    }
}
