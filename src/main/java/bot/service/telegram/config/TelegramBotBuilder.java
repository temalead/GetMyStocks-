package bot.service.telegram.config;

import bot.service.telegram.TelegramUpdateHandler;
import bot.service.telegram.WebhookBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;


@Configuration
@EnableConfigurationProperties(TelegramBotConfig.class)
@RequiredArgsConstructor
public class TelegramBotBuilder {
    private final TelegramBotConfig botConfig;

    /*@Bean
    public SetWebhook setWebhook(){
        System.out.println(botConfig);
        return SetWebhook.builder().url(botConfig.getBotPath()).build();
    }*/

    @Bean
    public WebhookBot webhookBot(TelegramUpdateHandler handler){

        WebhookBot webhookBot=new WebhookBot(handler);
        webhookBot.setBotUsername(botConfig.getBotUsername());
        webhookBot.setBotPath(botConfig.getBotPath());
        webhookBot.setBotToken(botConfig.getBotToken());
        return webhookBot;
    }
}
