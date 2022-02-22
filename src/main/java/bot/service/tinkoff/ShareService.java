package bot.service.tinkoff;


import bot.domain.dto.TickersDto;
import bot.domain.ShareDto;
import bot.domain.dto.SharePrice;
import bot.domain.dto.SharePriceDto;
import bot.exception.NotFoundShareException;
import bot.repository.ShareRepository;
import bot.service.tinkoff.utils.PriceCalculator;
import io.grpc.StatusRuntimeException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShareService {
    InvestApi api;
    ShareRepository repository;
    @Value("${bot.invest.code}")
    String classCode;


    public BigDecimal getLastDividendByTicker(String ticker) throws NotFoundShareException {
        Optional<ShareDto> share = repository.findById(ticker);
        if (share.isPresent()) {
            log.info("Found share {} in cache. Returning...", ticker);
            return share.get().getDividend();
        } else {
            log.info("Share with ticker {} not found in cache. Creating...", ticker);
            ShareDto newShareDto = createShare(ticker);
            return newShareDto.getDividend();
        }
    }


    @Async
    public CompletableFuture<Optional<Share>> getFigiByTicker(String ticker) {
        CompletableFuture<Optional<Share>> share = api.getInstrumentsService().getShareByTicker(ticker, classCode);
        log.info("Getting figi by ticker {}", ticker);
        return share;
    }

    public SharePriceDto getSharesPrices(TickersDto tickers) {
        List<CompletableFuture<Optional<Share>>> shareList = new ArrayList<>();
        tickers.getTickers().forEach(ticker -> shareList.add(getFigiByTicker(ticker)));
        List<String> figies = shareList.stream()
                .map(CompletableFuture::join)
                .map(share -> share.orElseThrow(() -> new NotFoundShareException("Share not found!")))
                .map(Share::getFigi).collect(Collectors.toList());

        List<LastPrice> pricesFromApi = api.getMarketDataService().getLastPrices(figies).join();

        List<SharePrice> prices = pricesFromApi.stream()
                .map(price -> new SharePrice(price.getFigi(), PriceCalculator.calculateSharePrice(price.getPrice())))
                .collect(Collectors.toList());
        return new SharePriceDto(prices);
    }


    private ShareDto createShare(String ticker) throws NotFoundShareException {
        Share share = getFigiByTicker(ticker).join().orElseThrow(() -> new NotFoundShareException("Share not found"));
        String figi = share.getFigi();

        List<Dividend> dividends = api.getInstrumentsService().getDividendsSync(figi,
                Instant.now().minus(365, ChronoUnit.DAYS),
                Instant.now());
        BigDecimal dividend = PriceCalculator.calculateShareDividends(dividends);
        log.info("Get dividens of {}", ticker);
        ShareDto shareDto = new ShareDto().setFigi(figi)
                .setId(ticker)
                .setDividend(dividend);

        repository.save(shareDto);

        return shareDto;
    }
}

