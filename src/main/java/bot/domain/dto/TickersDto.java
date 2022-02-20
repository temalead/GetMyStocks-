package bot.domain.dto;

import lombok.Value;

import java.util.List;

@Value
public class TickersDto {
    List<String> tickers;
}
