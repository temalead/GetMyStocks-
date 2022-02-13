package bot.service;

import bot.exception.NotFoundStockException;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockServiceTest {
    @Autowired
    private StockService service;

    @Test
    public void testGetLastDividendByTicker() throws NotFoundStockException {

        Long gazp = service.getLastDividendByTicker("GAZP");
        System.out.println(gazp);
    }

}