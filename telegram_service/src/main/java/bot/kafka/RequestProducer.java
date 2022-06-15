package bot.kafka;

import bot.entity.Request;
import bot.exception.sender.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(TopicsProperties.class)
public class RequestProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties topics;

    private final ObjectMapper mapper;

    @SneakyThrows
    public void sendRequest(Request request) {
        String value = mapper.writeValueAsString(request);
        Asset asset = request.getAsset();

        switch (asset){
            case SHARE:
                kafkaTemplate.send(topics.getShare(), value);
                log.info("Getting request to share topic");
                break;
            case BOND:
                kafkaTemplate.send(topics.getBond(),value);
                log.info("Getting request to bond topic");

                break;
            default:
                kafkaTemplate.send(topics.getPortfolio(),value);
                log.info("Getting request to portfolio topic");
        }


    }
}
