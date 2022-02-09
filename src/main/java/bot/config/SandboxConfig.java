package bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.invest")
public class SandboxConfig {
    private Boolean isSandBoxMode;
    private String token;
}
