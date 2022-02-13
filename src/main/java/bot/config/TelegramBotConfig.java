package bot.config;

import bot.service.telegram.Bot;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Data
@EnableConfigurationProperties(TelegramBotBuilder.class)
public class TelegramBotConfig {
    private final TelegramBotBuilder builder;

    @Bean
    public Bot bot() throws TelegramApiException {
        Bot bot = new Bot(builder.getToken(), builder.getName());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return bot;
    }

}
