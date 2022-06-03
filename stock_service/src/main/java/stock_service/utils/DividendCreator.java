package stock_service.utils;



import ru.tinkoff.piapi.contract.v1.Dividend;
import stock_service.entity.dto.DividendDto;
import stock_service.entity.dto.DividendListDto;

import java.util.ArrayList;
import java.util.List;

public class DividendCreator {
    public static DividendListDto createDividend(List<Dividend> dividends) {
        List<DividendDto> list = new ArrayList<>();
        dividends
                .forEach(dividend -> list.add(new DividendDto(PriceCalculator.calculateShareDividends(dividend),
                        DividendPaymentDateCreator.addPaymentDate(dividend.getPaymentDate()))));
        return new DividendListDto(list);
    }
}
