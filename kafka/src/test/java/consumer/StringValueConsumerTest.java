package consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import producer.KafkaBase;
import producer.Value;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.LongStream;

import static consumer.MyConsumer.TOPIC_NAME;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;


@Slf4j
class StringValueConsumerTest {


    @BeforeAll
    public static void init() throws ExecutionException, InterruptedException, TimeoutException {
        KafkaBase.start(List.of(new NewTopic(TOPIC_NAME, 1, (short) 1)));
    }

    @Test
    void dataHandlerTest() throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
        //given
        List<Value> stringValues = LongStream.range(0, 9).boxed()
                .map(idx -> new Value(idx, "test:" + idx))
                .toList();
        putValuesToKafka(stringValues);

        var myConsumer = new MyConsumer(KafkaBase.getBootstrapServers());

        List<Value> factStringValues = new CopyOnWriteArrayList<>();
        var dataConsumer = new StringValueConsumer(myConsumer, factStringValues::add);

        //when
        CompletableFuture.runAsync(dataConsumer::startSending);

        //then
        await().atMost(30, TimeUnit.SECONDS).until(() -> factStringValues.size() == stringValues.size());
        assertThat(factStringValues).hasSameElementsAs(stringValues);
        dataConsumer.stopSending();
    }

    private void putValuesToKafka(List<Value> stringValues) throws  com.fasterxml.jackson.core.JsonProcessingException {
        Properties props = new Properties();
        props.put(CLIENT_ID_CONFIG, "myKafkaTestProducer");
        props.put(BOOTSTRAP_SERVERS_CONFIG, KafkaBase.getBootstrapServers());
        props.put(ACKS_CONFIG, "0");
        props.put(LINGER_MS_CONFIG, 1);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        var mapper = new ObjectMapper();
        var kafkaProducer = new KafkaProducer<>(props);

        log.info("sending values, counter:{}", stringValues.size());
        for(var value: stringValues) {
            var valueAsString = mapper.writeValueAsString(value);
            kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, String.valueOf(value.id()), valueAsString));
        }
        log.info("done");
    }
}