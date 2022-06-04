package stock_service.sender;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import stock_service.entity.Portfolio;
import stock_service.entity.User;
import stock_service.entity.dto.SecurityDto;
import stock_service.entity.Asset;
import stock_service.utils.FractionOccupiedPortfolioCalculator;
import stock_service.utils.PortfolioCreator;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioCompositionSender implements Sender {
    PortfolioCreator creator;
    FractionOccupiedPortfolioCalculator calculator;


    @Override
    public SendMessage getInfo(Message message, User user) {

        String chatId = message.getChatId().toString();
        log.info("Got portfolio from user {}", user);

        Portfolio portfolio;

        if (user.getPortfolio() != null) {
            portfolio = user.getPortfolio();
        } else {
            portfolio = creator.createPortfolio(message, user);
        }

        List<SecurityDto> list = portfolio.getSecurities();

        StringBuilder stringBuilder = new StringBuilder();


        for (SecurityDto security : list) {
            stringBuilder.append(createMessage(security));
            BigDecimal fraction = calculator.calculateOccupiedFractionOfSecurityByUser(security, user);
            stringBuilder.append("Fraction of Portfolio:").append(fraction).append("%").append("\n");
            stringBuilder.append("\n");
        }

        String shareFraction = calculator.calculateFraction(portfolio, Asset.SHARE);
        String bondFraction = calculator.calculateFraction(portfolio, Asset.BOND);

        stringBuilder.append("Share fraction: ").append(shareFraction).append("\n");
        stringBuilder.append("Bond fraction: ").append(bondFraction).append("\n");


        return SendMessage.builder().chatId(chatId).text(stringBuilder.toString()).build();
    }

    public String createMessage(SecurityDto securityDto) {
        StringBuilder stringBuilder = new StringBuilder();

        BigDecimal price = securityDto.getPrice();
        String name = securityDto.getName();
        BigDecimal lot = securityDto.getLot();
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Quantity: ").append(lot).append("\n");

        return stringBuilder.toString();
    }
}
