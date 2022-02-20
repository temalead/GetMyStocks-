package bot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
public class SharePrice {
    String figi;
    BigDecimal price;
}
