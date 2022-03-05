package bot.tinkoff.utils;

import bot.domain.Stock;
import bot.domain.dto.DividendDto;
import bot.domain.dto.DividendListDto;

import java.math.BigDecimal;
import java.util.List;

public class ShareMessageCreator {
    public static String createMessage(Stock share) {
        System.out.println(share);

        String dividendMessage = createDividendMessage(share.getDividends());
        String price = "Price: " + share.getPrice() + "\n";

        return price + "\n" + dividendMessage;
    }

    private static String createDividendMessage(DividendListDto dividends) {
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
