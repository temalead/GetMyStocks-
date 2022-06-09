package stock_service.entity.bond;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@AllArgsConstructor
public class ACI {
    BigDecimal payment;
    LocalDate paymentDate;
}
