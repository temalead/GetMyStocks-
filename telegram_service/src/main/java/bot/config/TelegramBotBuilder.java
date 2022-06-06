package bot.config;

import bot.telegram.handler.UpdateHandler;
import bot.telegram.WebhookBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(TelegramBotConfig.class)
@RequiredArgsConstructor
public class TelegramBotBuilder {
    private final TelegramBotConfig botConfig;

    @Bean
    public WebhookBot webhookBot(UpdateHandler handler){

        WebhookBot webhookBot=new WebhookBot(handler);
        webhookBot.setBotUsername(botConfig.getBotUsername());
        webhookBot.setBotPath(botConfig.getBotPath());
        webhookBot.setBotToken(botConfig.getBotToken());

        return webhookBot;
    }
}
