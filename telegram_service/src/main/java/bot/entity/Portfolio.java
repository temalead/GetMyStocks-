package bot.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Data
public class Portfolio {
    BigDecimal portfolioValue;
    List<SecurityDto> securityList;
}
