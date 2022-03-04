package bot.tinkoff.utils;

import bot.domain.Stock;
import bot.domain.dto.DividendDto;
import bot.domain.dto.DividendListDto;

import java.util.List;

public abstract class ShareInfoSender {
    public static String createMessage(Stock share) {
        System.out.println(share);

        String dividendMessage = createDividendMessage(share.getDividends());
        String price="Цена: "+share.getPrice()+"\n";

        return price+"\n"+dividendMessage;
    }

    private static String createDividendMessage(DividendListDto dividends){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Дивиденды за год\n");
        List<DividendDto> list = dividends.getDividends();
        if (list.isEmpty()){
            return "Дивиденды не выплачивались";
        }
        else {
            for (DividendDto dto : list) {
                String payment = "Выплата: " + dto.getPayment() + "\n";
                String date = "Дата: " + dto.getPayment_date() + "\n";

                stringBuilder.append(payment).append(date);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }

}
