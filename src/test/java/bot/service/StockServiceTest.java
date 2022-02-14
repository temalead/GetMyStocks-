package bot.service;

import bot.exception.NotFoundStockException;
import bot.repository.ShareRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {
    @Autowired
    private StockService service;


    @Test
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