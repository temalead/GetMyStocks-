package bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.yml")
public class TelegramBotBuilder {
    @Value("${bot.telegram.token}")
    private String token;
    @Value("${bot.telegram.name}")
    private String name;
}
