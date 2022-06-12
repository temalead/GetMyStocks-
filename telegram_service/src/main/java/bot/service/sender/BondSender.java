package bot.service.sender;


import bot.entity.MyBond;
import bot.exception.BondNotFoundException;
import bot.repository.BondRepository;
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
public class BondSender implements SecuritySender {
    private final BondRepository repository;


    @Override
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();

        String ticker = message.getText();
        MyBond bond = repository.findById(ticker).orElseThrow(() -> new BondNotFoundException(ticker));
        String result = createMessage(bond);


        return SendMessage.builder().chatId(chatId).text(result).build();

    }


    private String createMessage(MyBond myBond) {
        StringBuilder stringBuilder = new StringBuilder();

        LocalDate date = myBond.getMaturityDate();
        BigDecimal price = myBond.getPrice();
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Maturity Date: ").append(date).append("\n");
        stringBuilder.append("Accrued interests: ").append(myBond.getAci()).append("\n");

        return stringBuilder.toString();
    }
}
