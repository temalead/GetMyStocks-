package bot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEncryptableProperties
@PropertySource(name = "EncryptedProperties",value = "classpath:encrypted.properties")
public class InvestServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(InvestServiceRunner.class,args);
    }
}
