package bot.tinkoff.utils;

import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;

public final class PriceCalculator {

    public static BigDecimal calculateValue(Quotation price) {
        long units = price.getUnits();
        BigDecimal nanos = BigDecimal.valueOf(price.getNano());
        BigDecimal divide = nanos.divide(BigDecimal.valueOf(1000000000L));
        return BigDecimal.valueOf(units).add(divide);
    }

    public static BigDecimal calculateShareDividends(Dividend dividend) {
        if (dividend!=null) {
            MoneyValue result = dividend.getDividendNet();
            return calculateValue(Quotation.newBuilder().setUnits(result.getUnits()).setNano(result.getNano()).build());
        } else {
            return BigDecimal.ZERO;
        }
    }


    public static BigDecimal calculateACI(MoneyValue value) {
        return calculateValue(Quotation.newBuilder().setNano(value.getNano()).setUnits(value.getUnits()).build());
    }
}
