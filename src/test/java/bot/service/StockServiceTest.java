package bot.service;

import bot.exception.NotFoundStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {
    @Autowired
    private StockService service;

    @Test
    public void testGetLastDividendByTicker() throws NotFoundStockException {

        BigDecimal gazp = service.getLastDividendByTicker("FEES");
        System.out.println(gazp.doubleValue());
    }

}