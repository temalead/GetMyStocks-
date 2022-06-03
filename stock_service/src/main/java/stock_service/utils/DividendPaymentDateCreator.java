package stock_service.utils;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DividendPaymentDateCreator {
    public static LocalDate addPaymentDate(Timestamp date){
        return Instant.ofEpochSecond(date.getSeconds()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
