package bot.service;


import bot.domain.dto.ShareDto;
import bot.exception.NotFoundStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final InvestApi api;
    private final String classCode = "TQBR";

    public Dividend getLasDividendByTicker(String ticker) throws NotFoundStockException {
        String figiOfStock = getFigiOfStock(ticker);

        List<Dividend> dividends = api.getInstrumentsService().getDividendsSync(figiOfStock,
                Instant.now().minus(1, ChronoUnit.YEARS),
                Instant.now());

        return dividends.get(0);
    }

    private String getFigiOfStock(String ticket) throws NotFoundStockException {
        Share share = api.getInstrumentsService().getShareByTickerSync(ticket, classCode).
                orElseThrow(() -> new NotFoundStockException(ticket));

        ShareDto shareDto = new ShareDto()
                .setFigi(share.getFigi())
                .setTicker(share.getTicker());
        return shareDto.getFigi();
    }

}
