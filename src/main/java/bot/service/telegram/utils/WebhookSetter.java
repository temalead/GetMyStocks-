package bot.service.telegram.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Component
public class WebhookSetter {
    @Value("${bot.telegram.botPath}")
    private String setWebhook;
    @Value("${bot.telegram.botToken}")
    private String token;

    @PostConstruct
    public void setWebHook() throws IOException {

        OkHttpClient client = new OkHttpClient();

        String s = "https://api.telegram.org/bot" + token + "/setWebhook?url=" + setWebhook;

        Request request = new Request.Builder()
                .url(s)
                .build();

        client.newCall(request).execute().close();

    }
}
