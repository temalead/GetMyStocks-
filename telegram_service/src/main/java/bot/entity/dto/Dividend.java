package bot.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@AllArgsConstructor
public class Dividend {
    BigDecimal payment;
    LocalDate paymentDate;
}
