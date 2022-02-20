package bot.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Value;
import ru.tinkoff.piapi.contract.v1.Dividend;
import ru.tinkoff.piapi.contract.v1.Share;

import java.util.List;

@Value
@AllArgsConstructor
public class DividendsDto {
    Share share;
    List<Dividend> dividends;

}
