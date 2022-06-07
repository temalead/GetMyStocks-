package bot.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "topic")
public class TopicsProperties {
    String share;
    String bond;
    String portfolio;
}
