package stock_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stock_service.entity.MyBond;
import stock_service.entity.MyShare;
import stock_service.entity.share.Dividend;
import stock_service.entity.share.DividendList;
import stock_service.exception.ShareNotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareMessageCreator {

    private final ShareService service;

    public String createMessage(String request) {

        MyShare share;
        try {
            share = service.getAssetFromTinkoffByTicker(request);
        } catch (ShareNotFoundException ex) {
            return ex.getMessage();
        }

        String dividendMessage = createDividendMessage(share.getDividends());
        String price = "Price: " + share.getPrice() + "\n";

        return price + "\n" + dividendMessage;
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
