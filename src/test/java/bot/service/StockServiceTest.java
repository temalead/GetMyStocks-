package bot.service;

import bot.exception.NotFoundStockException;
import bot.service.tinkoff.StockService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {

    @Autowired
    private StockService service;


    @Test
    @DisplayName("Test apply to api to create new stock")
    public void shouldReturnValueFromJustCreatedShare() throws Exception {

        service.getLastDividendByTicker("SBER");
    }

    @Test
    @DisplayName("Should return dividend with integer and float value")
    public void shouldReturnIntegerDiv() throws NotFoundStockException {

        BigDecimal expected = BigDecimal.valueOf(18.7);
        BigDecimal result = service.getLastDividendByTicker("SBER");

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnDoubleDiv() throws NotFoundStockException {
        double expected = BigDecimal.valueOf(0.016132865).doubleValue();
        double result = service.getLastDividendByTicker("FEES").doubleValue();

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnZeroDiv() throws NotFoundStockException {
        BigDecimal expected = BigDecimal.valueOf(0);
        BigDecimal result = service.getLastDividendByTicker("RUAL");

        assertEquals(expected, result);
    }
}