package consumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerRunner {
    public static void main(String[] args) {
        MyConsumer myConsumer = new MyConsumer("temapc:9092");
        StringValueConsumer consumer = new StringValueConsumer(myConsumer, value -> log.info("value: {}", value));
        consumer.startSending();

    }
}
