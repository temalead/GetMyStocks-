package bot.config;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Data
@ConfigurationProperties(prefix = "bot.telegram")
public class TelegramBotConfig {
    String botToken;
    String botUsername;
    String port;

    String proxyHost;
    int proxyPort;
    DefaultBotOptions.ProxyType proxyType;

}

