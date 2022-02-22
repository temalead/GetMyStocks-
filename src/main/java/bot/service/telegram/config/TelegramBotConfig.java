package bot.service.telegram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.telegram")
public class TelegramBotConfig {
    private String token;
    private String name;
    private String path;
}

