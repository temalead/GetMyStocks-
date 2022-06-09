package bot.config;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebhookSetter {

    private final TelegramBotConfig config;

    @PostConstruct
    public void setWebHook() throws IOException {

        OkHttpClient client = new OkHttpClient();

        String s = "https://api.telegram.org/bot" + config.getBotToken() + "/setWebhook?url=" + config.getBotPath();

        Request request = new Request.Builder()
                .url(s)
                .build();

        client.newCall(request).execute().close();

    }
}
