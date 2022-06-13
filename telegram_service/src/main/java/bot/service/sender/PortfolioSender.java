package bot.service.sender;


import bot.entity.Portfolio;
import bot.entity.Security;
import bot.entity.User;
import bot.exception.NonExistentPortfolioException;
import bot.exception.sender.Asset;
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

    //TODO portfolio actions: create, get, update, delete
    public SendMessage getInfo(User user) throws NonExistentPortfolioException {
        Portfolio portfolio = user.getPortfolio();

        //createSecurityMessage(portfolio);
        return null;
    }

/*
    public SendMessage createSecurityMessage(String chatId, Portfolio portfolio, User user) {


        List<Security> list = portfolio.getSecurityList();

        StringBuilder stringBuilder = new StringBuilder();


        for (Security security : list) {
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

    public String createSecurityMessage(Security security) {
        StringBuilder stringBuilder = new StringBuilder();

        BigDecimal price = security.getPrice();
        Integer lot = security.getLot();
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Quantity: ").append(lot).append("\n");

        return stringBuilder.toString();
    }*/
}
