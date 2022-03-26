package bot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
@EnableAsync
@EnableRedisRepositories
public class InvestServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(InvestServiceRunner.class,args);
    }
}
