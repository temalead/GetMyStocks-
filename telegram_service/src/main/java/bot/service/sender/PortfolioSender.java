package bot.service.sender;


import bot.entity.Portfolio;
import bot.entity.User;
import bot.entity.dto.SecurityDto;
import bot.exception.sender.Asset;
import bot.utils.FractionOccupiedPortfolioCalculator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioSender {

    FractionOccupiedPortfolioCalculator calculator;

    public SendMessage getInfo(String chatId, User user) {
        Portfolio portfolio = user.getPortfolio();

        return createSecurityMessage(chatId, portfolio, user);
    }

    public SendMessage createSecurityMessage(String chatId, Portfolio portfolio, User user) {


        List<SecurityDto> list = portfolio.getSecurityList();

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


        return SendMessage.builder().chatId(chatId).text(stringBuilder.toString()).build();
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
