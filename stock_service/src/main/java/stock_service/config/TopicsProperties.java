package stock_service.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "topic")
public class TopicsProperties {
    private String share;
    private String bond;
    private String portfolio;
    private String share_res;
    private String bond_res;
    private String portfolio_res;
}
