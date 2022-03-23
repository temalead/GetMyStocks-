package producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BUFFER_MEMORY_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_BLOCK_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Slf4j
public class Producer {
    private final KafkaProducer<String, String> kafkaProducer;
    static final String TOPIC_NAME = "MyTopic";

    public Producer(String bootstrapServers) {
        Properties props = new Properties();
        props.put(CLIENT_ID_CONFIG, "stocks");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ACKS_CONFIG, "1"); //количество подтсврежений
        props.put(RETRIES_CONFIG, 1);
        props.put(BATCH_SIZE_CONFIG, 16384); //размер батча
        props.put(LINGER_MS_CONFIG, 10);  //ждём батчи каждые 10 сек, иначе отправляем что есть
        props.put(BUFFER_MEMORY_CONFIG, 33_554_432); //bytes
        props.put(MAX_BLOCK_MS_CONFIG, 1_000); //ms
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProducer = new KafkaProducer<>(props);

        var shutdownHook = new Thread(() -> {
            log.info("closing kafka producer");
            kafkaProducer.close();
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public KafkaProducer<String, String> getMyProducer() {
        return kafkaProducer;
    }
}

