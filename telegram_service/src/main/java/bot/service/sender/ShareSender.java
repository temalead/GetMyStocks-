package bot.service.sender;


import bot.entity.MyShare;
import bot.entity.dto.DividendDto;
import bot.entity.dto.DividendListDto;
import bot.exception.ShareNotFoundException;
import bot.kafka.ShareConsumer;
import bot.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShareSender implements SecuritySender {
    private final ShareRepository repository;

    @Override
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();
        String ticker = message.getText();
        MyShare info = repository.findById(ticker).orElseThrow(()->new ShareNotFoundException(ticker));
        String result = createMessage(info);

        return SendMessage.builder().chatId(chatId).text(result).build();


    }

    public String createMessage(MyShare share) {

        String dividendMessage = createDividendMessage(share.getDividends());
        String price = "Price: " + share.getPrice() + "\n";

        return price + "\n" + dividendMessage;
    }

    private String createDividendMessage(DividendListDto dividends) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dividends per year\n");
        List<DividendDto> list = dividends.getDividends();
        if (list.isEmpty()) {
            return "Dividends were not paid";
        } else {
            for (DividendDto dto : list) {
                BigDecimal payment1 = dto.getPayment();
                String payment = "Payment: " + payment1 + "\n";
                String date = "Date: " + dto.getPaymentDate() + "\n";

                stringBuilder.append(payment).append(date);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
}



