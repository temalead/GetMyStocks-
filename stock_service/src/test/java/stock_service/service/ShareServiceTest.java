package stock_service.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;
import stock_service.entity.MyShare;
import stock_service.entity.share.Dividend;
import stock_service.entity.share.DividendList;
import stock_service.exception.ShareNotFoundException;
import stock_service.repository.ShareRepository;
import stock_service.utils.PriceCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShareServiceTest {

    final String exchangeCode = "TQBR";
    @Mock
    ShareRepository repository;
    InvestApi api;

    ShareService service;


    @BeforeEach
    void setUp() {
        api = Mockito.mock(InvestApi.class, Mockito.RETURNS_DEEP_STUBS);
        service = new ShareService(this.api, repository);
    }


    @Test
    void shouldUpdatePriceOfShareInDB() {
        //given
        final String ticker = "GAZP";
        final String figi = "AAA";
        MyShare shareInDB = MyShare.builder()
                .name(ticker)
                .dividends(new DividendList(List.of(Dividend.getDefaultDividend())))
                .figi(figi)
                .build();
        shareInDB.setPrice(BigDecimal.ZERO);
        Mockito.mockStatic(PriceCalculator.class);

        BigDecimal expected = BigDecimal.ONE;
        when(repository.findById(any())).thenReturn(Optional.of(shareInDB));
        when(PriceCalculator.calculateValue(any())).thenReturn(BigDecimal.ONE);

        //when
        MyShare result = service.getInfo(ticker);


        //then
        assertEquals(expected, result.getPrice());

    }

    @Test
    void shouldReturnPricesOfShares(){

    }


    @SneakyThrows
    @Test
    void shouldThrowShareNotFoundExceptionWhenFindingFigiOfShare() {
        final String ticker = "GAZSADF";
        CompletableFuture<Optional<Share>> futureWithException = new CompletableFuture<>();
        futureWithException.completeExceptionally(new ShareNotFoundException(ticker));
        when(api.getInstrumentsService().getShareByTicker(ticker, exchangeCode))
                .thenReturn(futureWithException);


        service.getFigiByTicker(ticker)
                .exceptionally(e -> {


                    throw new ShareNotFoundException(ticker);


                });


    }

}