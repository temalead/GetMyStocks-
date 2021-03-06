package stock_service.utils;



import stock_service.entity.share.Dividend;
import stock_service.entity.share.DividendList;

import java.util.ArrayList;
import java.util.List;

public class DividendCreator {
    public static DividendList createDividends(List<ru.tinkoff.piapi.contract.v1.Dividend> dividends) {
        List<Dividend> list = new ArrayList<>();
        dividends
                .forEach(dividend -> list.add(new Dividend(PriceCalculator.calculateShareDividends(dividend),
                        DividendPaymentDateCreator.addPaymentDate(dividend.getPaymentDate()))));
        return new DividendList(list);
    }
}
