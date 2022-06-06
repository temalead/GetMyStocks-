package bot.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "topic")
public class TopicsProperties {
    private String request;
    private String security;
}
