package stock_service.entity.share;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@AllArgsConstructor
public class SharePriceList {
    List<BigDecimal> prices;
}
