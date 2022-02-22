package bot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class DividendListDto {
    List<DividendDto> dividends;
}
