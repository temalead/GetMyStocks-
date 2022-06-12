package stock_service.entity.share;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@AllArgsConstructor
public class DividendList implements Serializable {
    List<Dividend> dividends;
}
