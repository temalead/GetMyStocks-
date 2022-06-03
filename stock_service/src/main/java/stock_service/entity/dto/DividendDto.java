package stock_service.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@AllArgsConstructor
public class DividendDto {
    BigDecimal payment;
    LocalDate paymentDate;
}
