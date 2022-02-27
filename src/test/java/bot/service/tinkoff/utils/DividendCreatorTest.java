package bot.service.tinkoff.utils;

import bot.tinkoff.utils.DivdendPaymentDateCreator;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.powermock.api.mockito.PowerMockito.mockStatic;


class DividendCreatorTest {

    @Test
    void createDividend() {
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(1645595284).build();

        LocalDate localDate = DivdendPaymentDateCreator.addPaymentDate(timestamp);

        assertEquals("2022-02-23",localDate.toString());
    }
}