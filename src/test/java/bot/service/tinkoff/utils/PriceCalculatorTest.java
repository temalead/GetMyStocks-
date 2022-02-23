package bot.service.tinkoff.utils;

import org.junit.jupiter.api.Test;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {

    @Test
    void calculateSharePrice() {
        Quotation quotation = Quotation.newBuilder().setNano(123456).setUnits(1234).build();
        BigDecimal bigDecimal = PriceCalculator.calculateSharePrice(quotation);
        System.out.println(bigDecimal);

        assertEquals("1234.000123456",bigDecimal.toString());
    }

    @Test
    void calculateShareDividends() {
    }
}