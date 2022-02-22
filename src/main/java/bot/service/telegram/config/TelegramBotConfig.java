package bot.service.telegram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.telegram")
public class TelegramBotConfig {
    private String botToken;
    private String botUsername;
    private String botPath;

}

