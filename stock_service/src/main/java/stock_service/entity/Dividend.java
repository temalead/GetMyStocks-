package stock_service.entity;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@AllArgsConstructor
public class Dividend {
    BigDecimal payment;
    LocalDate paymentDate;


    public static Dividend getDefaultDividend() {
       return new Dividend(BigDecimal.ZERO, LocalDate.MIN);
    }
}
