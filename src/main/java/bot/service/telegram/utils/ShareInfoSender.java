package bot.service.telegram.utils;

import bot.domain.ShareDto;
import bot.domain.dto.DividendDto;
import bot.domain.dto.DividendListDto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public abstract class ShareInfoSender {
    public static String createMessage(ShareDto share) {

        String dividendMessage = createDividendMessage(share.getDividends());
        String price="Цена:"+share.getPrice();

        return price+dividendMessage;
    }

    private static String createDividendMessage(DividendListDto dividends){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Дивиденды за год\n");
        List<DividendDto> list = dividends.getDividends();
        for (DividendDto dto : list) {
            String payment="Выплата: "+dto.getPayment()+"\n";
            String date="Дата: "+dto.getPayment_date()+"\n";

            stringBuilder.append(payment).append(date);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
