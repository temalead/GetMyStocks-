package stock_service.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@Configuration
@EnableKafka
@EnableScheduling
@RequiredArgsConstructor
public class KafkaProducerSecurity {


    private final KafkaProperties kafkaProperties;


    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }


    @Bean
    public NewTopic shareTopic() {
        return TopicBuilder
                .name("share.topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bondTopic() {
        return TopicBuilder
                .name("bond.topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic portfolioTopic() {
        return TopicBuilder
                .name("portfolio.topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
