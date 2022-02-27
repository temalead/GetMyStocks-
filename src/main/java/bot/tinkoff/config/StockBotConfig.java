package bot.tinkoff.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.invest")
public class StockBotConfig {
    private Boolean isSandBoxMode;
    private String token;
    private String code;
}
