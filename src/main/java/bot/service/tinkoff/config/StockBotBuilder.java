package bot.service.tinkoff.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@RequiredArgsConstructor
@EnableConfigurationProperties(StockBotConfig.class)
@Configuration
public class StockBotBuilder {
    private final StockBotConfig builder;

    @Bean
    public InvestApi investApi(){
        return InvestApi.create(builder.getToken());
    }
}
