package bot.kafka;

import bot.entity.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(TopicsProperties.class)
public class RequestProducer {
    KafkaTemplate<String, Request> kafkaTemplate;
    TopicsProperties topics;


    public void sendRequest(Request request) {

        kafkaTemplate.send(topics.getShare(), request);

    }
}
