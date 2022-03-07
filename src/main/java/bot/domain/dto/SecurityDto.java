package bot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
@Accessors(chain = true)
public class SecurityDto {
    BigDecimal price;
    String name;
}
