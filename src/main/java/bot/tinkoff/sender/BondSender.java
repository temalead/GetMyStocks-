package bot.tinkoff.sender;

import bot.entity.MyBond;
import bot.entity.User;
import bot.tinkoff.BondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class BondSender implements Sender{
    private final BondService bondService;

    @Override
    public SendMessage getInfo(Message message, User user) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        MyBond info = bondService.getInfo(text);
        String result= createMessage(info);
        log.info("Message: {}",result);
        return SendMessage.builder().chatId(chatId).text(result).build();
    }


    private String createMessage(MyBond myBond) {
        StringBuilder stringBuilder=new StringBuilder();

        LocalDate date = myBond.getMaturityDate();
        BigDecimal price = myBond.getPrice();
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Maturity Date: ").append(date).append("\n");
        stringBuilder.append("Accrued interests: ").append(myBond.getAci()).append("\n");

        return stringBuilder.toString();
    }
}
