package bot.kafka;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@Service
public class ShareRespondent extends Thread {

    private String message;
    private boolean running = true;

    @Override
    @SneakyThrows
    public void run() {

        while (running){

        }


    }


    public void sendMessageFromKafka(String message) {
        this.message = message;
    }

    @SneakyThrows
    public String getMessage() {
        while (this.message == null) {
            wait();
        }

        return this.message;
    }


    public void shutDown(){
        this.running=false;
    }
}
