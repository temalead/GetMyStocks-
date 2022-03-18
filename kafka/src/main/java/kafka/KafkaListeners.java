package kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(groupId = "tcs",
            topics = {"security_t"})
    void listener(String data){
        System.out.println("Listener recieved: "+data);
    }
}

