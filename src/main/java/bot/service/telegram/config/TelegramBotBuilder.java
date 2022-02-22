package bot.service.telegram.config;

import bot.service.telegram.TelegramUpdateHandler;
import bot.service.telegram.WebhookBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@EnableConfigurationProperties(TelegramBotConfig.class)
@RequiredArgsConstructor
public class TelegramBotBuilder {
    private final TelegramBotConfig botConfig;

    @Bean
    public SetWebhook setWebhook(){
        return SetWebhook.builder().url(botConfig.getPath()).build();
    }

    @Bean
    public WebhookBot webhookBot(SetWebhook webhook, TelegramUpdateHandler handler){
        WebhookBot webhookBot=new WebhookBot(webhook,handler);
        webhookBot.setBotUsername(botConfig.getName());
        webhookBot.setBotPath(botConfig.getPath());
        webhookBot.setBotToken(botConfig.getToken());
        return webhookBot;
    }
}
