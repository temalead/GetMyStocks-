package stock_service.entity.dto;

import lombok.Value;

import java.util.List;

@Value
public class TickersDto {
    List<String> tickers;
}
