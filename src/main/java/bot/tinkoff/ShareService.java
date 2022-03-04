package bot.tinkoff;


import bot.domain.Stock;
import bot.domain.dto.DividendListDto;
import bot.domain.dto.SharePriceListDto;
import bot.exception.NotFoundShareException;
import bot.repository.ShareRepository;
import bot.tinkoff.utils.DividendCreator;
import bot.tinkoff.utils.PriceCalculator;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Collections;
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
    String code = "TQBR";

    @NonNull
    public Stock getInfo(String ticker) {
        Optional<Stock> share = repository.findById(ticker);
        if (share.isPresent()) {
            log.info("Found share {} in cache. Returning...", ticker);
            updatePrice(share.get());
            return share.get();
        } else {
            log.info("Share with ticker {} not found in cache. Creating...", ticker);
            return createShare(ticker);
        }
    }


    private void updatePrice(Stock stock) {
        stock.setPrice(getSharePrice(stock.getFigi()));
        repository.save(stock);
    }


    @Async
    public CompletableFuture<Optional<Share>> getFigiByTicker(String ticker) {
        log.info("Getting figi by ticker {}", ticker);
        CompletableFuture<Optional<Share>> share = api.getInstrumentsService().getShareByTicker(ticker, code)
                .exceptionally(exception->{
                    log.error("Share {} not found",ticker);
                    throw new NotFoundShareException(ticker);
                });
        return share;
    }

    public BigDecimal getSharePrice(String figi){
        return PriceCalculator.calculateSharePrice(api.getMarketDataService().getLastPrices(Collections.singleton(figi)).join().get(0).getPrice());
    }


    public SharePriceListDto getSharesPrices(List<String> tickers) {
        List<CompletableFuture<Optional<Share>>> shareList = new ArrayList<>();
        tickers.forEach(ticker -> shareList.add(getFigiByTicker(ticker)));
        List<String> figies = shareList.stream()
                .map(CompletableFuture::join)
                .map(share -> share.orElseThrow(() -> new NotFoundShareException("Share not found!")))
                .map(Share::getFigi).collect(Collectors.toList());

        List<LastPrice> pricesFromApi = api.getMarketDataService().getLastPrices(figies).join();


        List<BigDecimal> prices = pricesFromApi.stream()
                .map(price -> PriceCalculator.calculateSharePrice(price.getPrice()))
                .collect(Collectors.toList());
        return new SharePriceListDto(prices);
    }


    private Stock createShare(String ticker) throws NotFoundShareException {
        Share share = getFigiByTicker(ticker).join().orElseThrow(() -> new NotFoundShareException("Share not found"));
        String figi = share.getFigi();


        List<Dividend> dividendsPerYear = api.getInstrumentsService().getDividends(figi,
                        Instant.now().minus(365, ChronoUnit.DAYS),
                        Instant.now())
                .join();
        DividendListDto dividends = DividendCreator.createDividend(dividendsPerYear);
        log.info("Get dividens of {}", ticker);
        SharePriceListDto prices = getSharesPrices(List.of(ticker));
        BigDecimal price = prices.getPrices().get(0);
        Stock stock = new Stock()
                .setPrice(price)
                .setFigi(figi)
                .setId(ticker)
                .setDividends(dividends);

        repository.save(stock);

        return stock;
    }
}

