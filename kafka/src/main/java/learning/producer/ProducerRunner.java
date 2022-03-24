package learning.producer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerRunner {
    public static void main(String[] args) {
        Producer producer = new Producer("localhost:9092");

        DataSender dataProducer = new DataSender(producer, value -> log.info("asked, value:{}", value));
        StringValueSource valueSource = new StringValueSource(dataProducer::dataHandle);
        valueSource.generate();

    }
}
