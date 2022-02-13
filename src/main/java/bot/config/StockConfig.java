package bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SandboxConfig.class)
@RequiredArgsConstructor
public class StockConfig {
    private final SandboxConfig sandboxConfig;

}
