package bot.service.tinkoff;


import bot.domain.dto.ShareDto;
import bot.exception.NotFoundStockException;
import bot.repository.ShareRepository;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import javax.ws.rs.NotFoundException;
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

    public BigDecimal getLastDividendByTicker(String ticker){
        Optional<ShareDto> share = shareRepository.findByTicker(ticker);
        if (share.isPresent()) {
            return share.get().getDividend();
        } else {
            log.info("Share with ticker {} not found in cache. Creating...", ticker);
            return createStock(ticker);
        }
    }

    private BigDecimal createStock(String ticker) {
        String figiOfStock = getFigiByStock(ticker);

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


    private String getFigiByStock(String ticket)  {

        try {
            Optional<Share> shareOpt = api.getInstrumentsService().getShareByTickerSync(ticket, classCode);
            Share share = shareOpt.orElseThrow(NotFoundException::new);
            ShareDto shareDto = new ShareDto()
                    .setFigi(share.getFigi())
                    .setTicker(share.getTicker());
            return shareDto.getFigi();
        } catch (StatusRuntimeException e) {
            return NotFoundShareMessageBuilder.createMsgError(ticket);
        }

    }

    private BigDecimal calculateExactDividend(List<Dividend> dividends) {
        if (dividends.size() != 0) {
            Dividend dividend = dividends.get(0);
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
