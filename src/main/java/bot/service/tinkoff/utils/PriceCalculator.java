package bot.service.tinkoff.utils;

import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;
import java.util.List;

public final class PriceCalculator {

    public static BigDecimal calculateSharePrice(Quotation price) {
        long units = price.getUnits();
        BigDecimal nanos = BigDecimal.valueOf(price.getNano());
        BigDecimal divide = nanos.divide(BigDecimal.valueOf(1000000000L));
        return BigDecimal.valueOf(units).add(divide);
    }

    public static BigDecimal calculateShareDividends(Dividend dividend) {
        if (dividend!=null) {
            long units = dividend.getDividendNet().getUnits();
            BigDecimal nanos = BigDecimal.valueOf(
                    dividend.getDividendNet()
                            .getNano()
            );
            BigDecimal divide = nanos.divide(BigDecimal.valueOf(1000000000L));
            return BigDecimal.valueOf(units).add(divide);
        } else {
            return BigDecimal.ZERO;
        }
    }

}
