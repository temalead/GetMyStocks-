package bot.tinkoff.sender;

import bot.entity.dto.SecurityDto;
import bot.tinkoff.utils.PortfolioCreator;
import bot.tinkoff.utils.ShareOccupiedPortfolioCalculator;
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
    ShareOccupiedPortfolioCalculator calculator;


    @Override
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();

        List<SecurityDto> portfolio = creator.createPortfolio(message);
        StringBuilder stringBuilder = new StringBuilder();


        for (SecurityDto security : portfolio) {
            stringBuilder.append(createMessage(security));
            String fraction = calculator.calculateOccupiedShareOfSecurityByUser(security, chatId);
            stringBuilder.append("Fraction: ").append(fraction).append("\n");
            stringBuilder.append("\n");

        }

        return SendMessage.builder().chatId(chatId).text(stringBuilder.toString()).build();
    }

    private String createMessage(SecurityDto securityDto) {
        StringBuilder stringBuilder = new StringBuilder();

        BigDecimal price = securityDto.getPrice();
        String name = securityDto.getName();
        Integer lot = securityDto.getLot();
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Quantity: ").append(lot).append("\n");

        return stringBuilder.toString();
    }
}
