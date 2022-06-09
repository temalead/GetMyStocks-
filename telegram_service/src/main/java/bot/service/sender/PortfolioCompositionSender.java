package bot.service.sender;


import bot.entity.User;
import bot.exception.NonExistentPortfolioException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioCompositionSender {

    //TODO portfolio actions: create, get, update, delete
    public SendMessage getInfo(Message message, User user) throws NonExistentPortfolioException {
        return null;
    }

    /*PortfolioCreator creator;
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
    }*/
}
