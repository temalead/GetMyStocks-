package bot.service;


import bot.domain.dto.ShareDto;
import bot.exception.NotFoundStockException;
import bot.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final InvestApi api;
    private final ShareRepository shareRepository;
    private final String classCode = "TQBR";

    public BigDecimal getLastDividendByTicker(String ticker) throws NotFoundStockException {
        Optional<ShareDto> share = shareRepository.findByTicker(ticker);
        if (share.isPresent()){
            return share.get().getDividend();
        }
        else {
            return createStock(ticker);
        }
    }

    private BigDecimal createStock(String ticker) throws NotFoundStockException {
        String figiOfStock = getFigiOfStock(ticker);

        List<Dividend> dividends = api.getInstrumentsService().getDividendsSync(figiOfStock,
                Instant.now().minus(365, ChronoUnit.DAYS),
                Instant.now());
        BigDecimal dividend = calculateExactDividend(dividends);
        log.info("Get dividens of {}", ticker);
        ShareDto stock = new ShareDto().setFigi(figiOfStock)
                .setTicker(ticker)
                .setDividend(dividend);

         shareRepository.save(stock);

         return stock.getDividend();
    }


    private String getFigiOfStock(String ticket) throws NotFoundStockException {
        ru.tinkoff.piapi.contract.v1.Share share = api.getInstrumentsService().getShareByTickerSync(ticket, classCode).
                orElseThrow(() -> new NotFoundStockException(ticket));

        ShareDto shareDto = new ShareDto()
                .setFigi(share.getFigi())
                .setTicker(share.getTicker());
        return shareDto.getFigi();
    }

    private BigDecimal calculateExactDividend(List<Dividend> dividends) {
        if (dividends.size()!=0) {
            Dividend dividend = dividends.get(0);
            long units = dividend.getDividendNet().getUnits();
            BigDecimal nanos = BigDecimal.valueOf(
                    dividend.getDividendNet()
                            .getNano()
            );
            BigDecimal divide = nanos.divide(BigDecimal.valueOf(1000000000L));
            return BigDecimal.valueOf(units).add(divide);
        }
        else {
            return BigDecimal.ZERO;
        }
    }
}
