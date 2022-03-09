package bot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class DividendListDto {
    List<DividendDto> dividends;
}
