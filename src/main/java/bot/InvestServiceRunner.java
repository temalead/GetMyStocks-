package bot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEncryptableProperties
@EnableAsync
@PropertySource(name = "EncryptedProperties",value = "classpath:encrypted.properties")
public class InvestServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(InvestServiceRunner.class,args);
    }
}
