package bot.tinkoff.sender;

import bot.entity.Portfolio;
import bot.entity.dto.SecurityDto;
import bot.exception.sender.Asset;
import bot.tinkoff.utils.PortfolioCreator;
import bot.tinkoff.utils.FractionOccupiedPortfolioCalculator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

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
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();

        Portfolio portfolio = creator.createPortfolio(message);
        List<SecurityDto> list = portfolio.getSecurityList();
        StringBuilder stringBuilder = new StringBuilder();


        for (SecurityDto security : list) {
            stringBuilder.append(createMessage(security));
            String fraction = calculator.calculateOccupiedFractionOfSecurityByUser(security, chatId);
            stringBuilder.append(fraction).append("\n");
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
