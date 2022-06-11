package stock_service;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import stock_service.config.TopicsProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableKafka
@EnableEncryptableProperties
@PropertySource(name = "EncryptedProperties",value = "classpath:encrypted.properties")
public class StockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

}
