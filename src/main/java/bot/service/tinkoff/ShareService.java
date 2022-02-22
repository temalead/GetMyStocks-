package bot.service.tinkoff;


import bot.domain.dto.DividendListDto;
import bot.domain.ShareDto;
import bot.domain.dto.SharePriceListDto;
import bot.exception.NotFoundShareException;
import bot.repository.ShareRepository;
import bot.service.tinkoff.utils.DividendCreator;
import bot.service.tinkoff.utils.PriceCalculator;
import lombok.AccessLevel;
import lombok.NonNull;
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
    String code="TQBR";

    @NonNull
    public ShareDto getInfo(String ticker) {
        Optional<ShareDto> share = repository.findById(ticker);
        if (share.isPresent()) {
            log.info("Found share {} in cache. Returning...", ticker);
            return share.get();
        } else {
            log.info("Share with ticker {} not found in cache. Creating...", ticker);
            return createShare(ticker);
        }
    }


    @Async
    public CompletableFuture<Optional<Share>> getFigiByTicker(String ticker) {
        CompletableFuture<Optional<Share>> share = api.getInstrumentsService().getShareByTicker(ticker, code);
        log.info("Getting figi by ticker {}", ticker);
        return share;
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


    private ShareDto createShare(String ticker) throws NotFoundShareException {
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
        ShareDto shareDto = new ShareDto()
                .setPrice(price)
                .setFigi(figi)
                .setId(ticker)
                .setDividends(dividends);

        repository.save(shareDto);

        return shareDto;
    }
}

