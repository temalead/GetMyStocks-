package bot.service.tinkoff;


import bot.domain.dto.FigiesDto;
import bot.domain.dto.ShareDto;
import bot.domain.dto.SharePrice;
import bot.domain.dto.SharePriceDto;
import bot.exception.NotFoundShareException;
import bot.repository.ShareRepository;
import bot.service.tinkoff.utils.PriceCalculator;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareService {
    private final InvestApi api;
    private final ShareRepository repository;
    private final String classCode = "TQBR";

    public BigDecimal getLastDividendByTicker(String ticker) throws NotFoundShareException {
        Optional<ShareDto> share = repository.findById(ticker);
        if (share.isPresent()) {
            log.info("Found share {} in cache. Returning...", ticker);
            return share.get().getDividend();
        } else {
            log.info("Share with ticker {} not found in cache. Creating...", ticker);
            return createStock(ticker);
        }
    }


    public SharePriceDto getShares(FigiesDto figies) {
        List<LastPrice> pricesFromApi = api.getMarketDataService().getLastPrices(figies.getFigies()).join();

        List<SharePrice> prices = pricesFromApi.stream()
                .map(price -> new SharePrice(price.getFigi(), PriceCalculator.calculateSharePrice(price.getPrice())))
                .collect(Collectors.toList());
        return new SharePriceDto(prices);
    }


    private BigDecimal createStock(String ticker) throws NotFoundShareException {
        String figiOfStock = getFigiByShare(ticker);

        List<Dividend> dividends = api.getInstrumentsService().getDividendsSync(figiOfStock,
                Instant.now().minus(365, ChronoUnit.DAYS),
                Instant.now());
        BigDecimal dividend = PriceCalculator.calculateShareDividends(dividends);
        log.info("Get dividens of {}", ticker);
        ShareDto stock = new ShareDto().setFigi(figiOfStock)
                .setId(ticker)
                .setDividend(dividend);

        repository.save(stock);

        return stock.getDividend();
    }


    private String getFigiByShare(String ticker) throws NotFoundShareException {

        try {
            Optional<Share> shareOpt = api.getInstrumentsService().getShareByTickerSync(ticker, classCode);
            Share share = shareOpt.orElseThrow(NotFoundException::new);
            ShareDto shareDto = new ShareDto()
                    .setFigi(share.getFigi())
                    .setId(share.getTicker());
            return shareDto.getFigi();
        } catch (StatusRuntimeException e) {
            log.error("Share with {} not exist!", ticker);
            throw new NotFoundShareException(ticker);
        }

    }



}

