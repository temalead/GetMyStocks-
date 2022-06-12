package stock_service.entity.share;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@AllArgsConstructor
public class Dividend implements Serializable {
    BigDecimal payment;
    LocalDate paymentDate;


    public static Dividend getDefaultDividend() {
       return new Dividend(BigDecimal.ZERO, LocalDate.MIN);
    }
}
