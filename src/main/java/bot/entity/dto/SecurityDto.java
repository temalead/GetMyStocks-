package bot.entity.dto;

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
    Integer lot;
    boolean isShare;
}
