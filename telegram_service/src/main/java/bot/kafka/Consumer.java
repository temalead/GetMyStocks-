package bot.kafka;

import java.util.concurrent.Callable;

public abstract class Consumer implements Callable<String> {

    String message;

    public void shutdown() {
        this.message = null;
    }

}
