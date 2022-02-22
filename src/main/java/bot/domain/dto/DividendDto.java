package bot.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Value;
import ru.tinkoff.piapi.contract.v1.Dividend;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class DividendDto {
    Dividend dividends;
    LocalDateTime payment_date;

}
