package bot.service;

import bot.exception.NotFoundShareException;
import bot.service.tinkoff.StockService;
import io.grpc.StatusRuntimeException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {

    @Autowired
    private StockService service;


    @Test
    @DisplayName("Should throw exception cause share with this ticker doesn`t exist")
    public void throwStatusRuntimeException() throws NotFoundShareException {
        String nonexistentTicker="saFASDF";
        String expectedMsg= String.format("Акция с тикером %s не существует!",nonexistentTicker);


        assertThrows(NotFoundShareException.class,()->service.getLastDividendByTicker(nonexistentTicker));

    }

    @Test
    @DisplayName("Should return dividend with integer and float value")
    public void shouldReturnIntegerDiv() throws NotFoundShareException {

        BigDecimal expected = BigDecimal.valueOf(18.7);
        BigDecimal result = service.getLastDividendByTicker("SBER");

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnDoubleDiv() throws NotFoundShareException {
        double expected = BigDecimal.valueOf(0.016132865).doubleValue();
        double result = service.getLastDividendByTicker("FEES").doubleValue();

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnZeroDiv() throws NotFoundShareException {
        BigDecimal expected = BigDecimal.valueOf(0);
        BigDecimal result = service.getLastDividendByTicker("RUAL");

        assertEquals(expected, result);
    }
}