package bot.config;

import bot.telegram.handler.UpdateHandler;
import bot.telegram.WebhookBot;
import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Proto;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
@EnableConfigurationProperties(TelegramBotConfig.class)
@RequiredArgsConstructor
public class TelegramBotBuilder {
    private final TelegramBotConfig botConfig;


    @Bean
    public WebhookBot webhookBot(UpdateHandler handler) {

        WebhookBot webhookBot = new WebhookBot(handler);
        webhookBot.setBotUsername(botConfig.getBotUsername());
        webhookBot.setBotPath(createBotPath());
        webhookBot.setBotToken(botConfig.getBotToken());

        return webhookBot;
    }


    private String createBotPath() {
        final NgrokClient ngrokClient = new NgrokClient.Builder().build();


        final CreateTunnel sshCreateTunnel = new CreateTunnel.Builder()
                .withProto(Proto.HTTP)
                .withAddr(8082)
                .build();
        final Tunnel tunnel = ngrokClient.connect(sshCreateTunnel);
        String url = tunnel.getPublicUrl();

        return Stream.of(url.split("(?=:)"))
                .map(String::new)
                .map(s -> {
                    if (s.startsWith("http")) {
                        s += "s";
                    }
                    return s;
                })
                .collect(Collectors.joining());
    }
}

