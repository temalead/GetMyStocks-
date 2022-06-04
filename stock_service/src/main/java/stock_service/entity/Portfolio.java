package stock_service.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import stock_service.entity.dto.SecurityDto;

import java.math.BigDecimal;
import java.util.List;

@Component
@Data
public class Portfolio {
    BigDecimal portfolioValue;
    List<SecurityDto> securities;
}
