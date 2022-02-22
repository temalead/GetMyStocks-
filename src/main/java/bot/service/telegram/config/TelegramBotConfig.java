package bot.service.telegram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Data
@ConfigurationProperties(prefix = "bot.telegram")
public class TelegramBotConfig {
    private String botToken;
    private String botUsername;
    private String botPath;

    private String proxyHost;
    private int proxyPort;
    private DefaultBotOptions.ProxyType proxyType;

}

