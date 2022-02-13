package bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@RequiredArgsConstructor
@EnableConfigurationProperties(StockBotBuilder.class)
@Configuration
public class StockBotConfig {
    private final StockBotBuilder builder;

    @Bean
    public InvestApi investApi(){
        return InvestApi.create(builder.getToken());
    }
}
