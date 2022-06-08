package stock_service.service;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;
import stock_service.exception.ShareNotFoundException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShareServiceTest {

    @Mock
    ShareRepository repository;
    InvestApi api;

    ShareService service;


    @BeforeEach
    void setUp() {
        api = Mockito.mock(InvestApi.class, Mockito.RETURNS_DEEP_STUBS);
        service = new ShareService(this.api, repository);
    }


    @SneakyThrows
    @Test
    void shouldThrowShareNotFoundExceptionWhenFindingFigiOfShare() {
        final String ticker = "GAZSADF";
        CompletableFuture<Optional<Share>> futureWithException = new CompletableFuture<>();
        futureWithException.completeExceptionally(new ShareNotFoundException(ticker));
        when(api.getInstrumentsService().getShareByTicker(ticker, "TQBR"))
                .thenReturn(futureWithException);


        CompletableFuture<Optional<Share>> actual = service.getFigiByTicker(ticker)
                .exceptionally(e -> {

                    try {

                        throw new ShareNotFoundException(ticker);
                    } catch (ShareNotFoundException ex) {
                        System.out.println("Exception was caught");
                        Assertions.fail();
                    }

                    return null;
                });


    }


}