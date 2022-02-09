package bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

@Configuration
@EnableConfigurationProperties(SandboxConfig.class)
@RequiredArgsConstructor
public class StockConfig {
    private final SandboxConfig sandboxConfig;

    @Bean
    public OpenApi openApi(){
        return new OkHttpOpenApi(sandboxConfig.getToken(), sandboxConfig.getIsSandBoxMode());
    }
}
