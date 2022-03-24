package bot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@AllArgsConstructor
public class SharePriceListDto {
    List<BigDecimal> prices;
}
