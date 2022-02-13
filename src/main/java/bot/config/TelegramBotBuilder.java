package bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.telegram")
public class TelegramBotBuilder {
    private String token;
    private String name;
}
