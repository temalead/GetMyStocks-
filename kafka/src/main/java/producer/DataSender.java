package producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.function.Consumer;

import static producer.Producer.TOPIC_NAME;

@Slf4j
public class DataSender {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Producer producer;
    private final Consumer<Value> sendAsk;

    public DataSender(Producer producer, Consumer<Value> sendAsk) {
        this.producer = producer;
        this.sendAsk = sendAsk;
    }

    public void dataHandle(Value value) {
        log.info("value: {}", value);
        String valueAsString = null;
        try {
            valueAsString = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        producer.getMyProducer().send(new ProducerRecord<>(TOPIC_NAME, String.valueOf(value.id()), valueAsString),
                (metadata, exception) -> {
                    if (exception != null) {
                        log.error("msg was not sent", exception);
                    } else {
                        log.info("message id:{} was sent, offset:{}", value.id(), metadata.offset());
                    }
                });
    }
}
