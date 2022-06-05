package stock_service.sender;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import stock_service.entity.MyShare;
import stock_service.entity.User;
import stock_service.entity.dto.DividendDto;
import stock_service.entity.dto.DividendListDto;
import stock_service.service.ShareService;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShareSender implements Sender {
    private final ShareService service;

    @Override
    public SendMessage getInfo(Message message, User user) {
        String chatId = message.getChatId().toString();

        MyShare info = service.getInfo(message.getText());
        String result = createMessage(info);
        return SendMessage.builder().chatId(chatId).text(result).build();

    }

    public String createMessage(MyShare share) {
        System.out.println(share);

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


