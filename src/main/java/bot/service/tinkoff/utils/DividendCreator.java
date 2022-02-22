package bot.service.tinkoff.utils;


import bot.domain.dto.DividendDto;
import bot.domain.dto.DividendListDto;
import ru.tinkoff.piapi.contract.v1.Dividend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DividendCreator {
    public static DividendListDto createDividend(List<Dividend> dividends) {
        List<DividendDto> list = new ArrayList<>();
        dividends
                .forEach(dividend -> list.add(new DividendDto(PriceCalculator.calculateShareDividends(dividend),
                        LocalDate.ofEpochDay(dividend.getPaymentDate().getNanos()))));
        return new DividendListDto(list);
    }
}
