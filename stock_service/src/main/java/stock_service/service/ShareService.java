package stock_service.service;


import stock_service.entity.MyShare;
import stock_service.entity.share.DividendList;
import stock_service.entity.share.SharePriceList;
import stock_service.exception.ShareNotFoundException;
import stock_service.repository.ShareRepository;
import stock_service.utils.DividendCreator;
import stock_service.utils.PriceCalculator;
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
public class ShareService implements SecurityService{
    InvestApi api;
    ShareRepository repository;
    String code = "TQBR";

    @NonNull
    @Override
    public MyShare getInfo(String ticker) {
        Optional<MyShare> share = repository.findById(ticker);
        if (share.isPresent()) {
            log.info("Found share {} in cache. Returning...", ticker);
            updatePrice(share.get());
            return share.get();
        } else {
            log.info("Share with ticker {} not found in cache. Creating...", ticker);
            return createShare(ticker);
        }
    }

    private MyShare createShare(String ticker) throws ShareNotFoundException {
        Share share = getFigiByTicker(ticker).join().orElseThrow(() -> new ShareNotFoundException("Share not found"));
        String figi = share.getFigi();
        DividendList dividends = getDividendsForYear(figi);
        BigDecimal price = getSharePrice(figi);
        MyShare myShare = new MyShare()
                .setFigi(figi)
                .setDividends(dividends);
        myShare.setPrice(price);
        myShare.setId(ticker);

        repository.save(myShare);

        return myShare;
    }

    @Async
    public CompletableFuture<Optional<Share>> getFigiByTicker(String ticker) {
        log.info("Getting figi by ticker {}", ticker);
        return api.getInstrumentsService().getShareByTicker(ticker, code)
                .exceptionally(exception -> {
                    log.error("Share {} not found", ticker);
                    throw new ShareNotFoundException(ticker);
                });
    }


    private DividendList getDividendsForYear(String figi) {
        List<Dividend> dividendList = api.getInstrumentsService().getDividends(figi,
                        Instant.now().minus(365, ChronoUnit.DAYS),
                        Instant.now())
                .join();
        return DividendCreator.createDividends(dividendList);
    }


    private void updatePrice(MyShare myShare) {
        myShare.setPrice(getSharePrice(myShare.getFigi()));
        repository.save(myShare);
    }

    private BigDecimal getSharePrice(String figi) {
        return PriceCalculator.calculateValue(api.getMarketDataService().getLastPrices(Collections.singleton(figi)).join().get(0).getPrice());
    }


    public List<MyShare> createShareCollection(List<String> tickers) {
        List<MyShare> result = new ArrayList<>();
        tickers.forEach(ticker -> result.add(getInfo(ticker)));
        return result;
    }


    private Share findShareByName(String name) {
        return api.getInstrumentsService().getAllSharesSync()
                .stream()
                .filter(share -> share.getName().equals(name)).findFirst().orElseThrow(() -> new ShareNotFoundException(name));
    }

    private SharePriceList getSharesPrices(List<String> tickers) {
        List<CompletableFuture<Optional<Share>>> shareList = new ArrayList<>();
        tickers.forEach(ticker -> shareList.add(getFigiByTicker(ticker)));
        List<String> figies = shareList.stream()
                .map(CompletableFuture::join)
                .map(share -> share.orElseThrow(() -> new ShareNotFoundException("Share not found!")))
                .map(Share::getFigi).collect(Collectors.toList());

        List<LastPrice> pricesFromApi = api.getMarketDataService().getLastPrices(figies).join();


        List<BigDecimal> prices = pricesFromApi.stream()
                .map(price -> PriceCalculator.calculateValue(price.getPrice()))
                .collect(Collectors.toList());
        return new SharePriceList(prices);
    }
}

