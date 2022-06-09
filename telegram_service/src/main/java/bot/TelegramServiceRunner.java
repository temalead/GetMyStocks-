package bot;

import bot.kafka.TopicsProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableEncryptableProperties
@EnableAsync
@EnableRedisRepositories
@EnableKafka
@PropertySource(name = "EncryptedProperties",value = "classpath:encrypted.properties")
public class TelegramServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(TelegramServiceRunner.class, args);
    }
}
