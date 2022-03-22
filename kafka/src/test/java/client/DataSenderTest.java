package client;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

class DataSenderTest {

    @Test
    void dataHandle() {
        //given
        List<Value> stringValues = LongStream.range(0, 9).boxed()
                .map(idx -> new Value(idx, "test: " + idx)).toList();

        Producer producer = new Producer(KafkaBase.getBootstrapServers());
        List<Value> factStringValues=new CopyOnWriteArrayList<>();
        DataSender sender = new DataSender(producer, factStringValues::add);
        ValueSource source = () -> {
            for (Value value : stringValues) {
                sender.dataHandle(value);
            }
        };

        //when
        source.generate();

        //then
        await().atMost(60, TimeUnit.SECONDS).until(()->factStringValues.size()==stringValues.size());
        assertThat(factStringValues).isEqualTo(stringValues);

    }
}