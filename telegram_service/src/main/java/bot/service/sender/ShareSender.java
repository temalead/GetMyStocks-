package bot.service.sender;


import bot.entity.MyShare;
import bot.entity.dto.Dividend;
import bot.entity.dto.DividendList;
import bot.exception.ShareNotFoundException;
import bot.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
        MyShare info = getInfoFromDB(ticker);
        String result = createMessage(info);

        return SendMessage.builder().chatId(chatId).text(result).build();


    }

    public String createMessage(MyShare share) {

        String dividendMessage = createDividendMessage(share.getDividends());
        String price = "Price: " + share.getPrice() + "\n";

        return price + "\n" + dividendMessage;
    }


    @SneakyThrows
    private MyShare getInfoFromDB(String ticker) {
        Thread.sleep(500);
        return repository.findById(ticker).orElseThrow(() -> new ShareNotFoundException(ticker));
    }

    private String createDividendMessage(DividendList dividends) {
        StringBuilder stringBuilder = new StringBuilder();
        if (dividends == null) {
            return stringBuilder.append("Dividends were not paid").toString();
        }
        stringBuilder.append("Dividends per year\n");
        List<Dividend> list = dividends.getDividends();
        if (list.isEmpty()) {
            return "Dividends were not paid";
        } else {
            for (Dividend dto : list) {
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



