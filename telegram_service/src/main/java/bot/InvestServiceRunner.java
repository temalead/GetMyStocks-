package bot;

import bot.kafka.TopicsProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableEncryptableProperties
@EnableAsync
@EnableRedisRepositories
@EnableConfigurationProperties(TopicsProperties.class)
public class InvestServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(InvestServiceRunner.class, args);
    }
}
