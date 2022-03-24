package learning.consumer;

public class ConsumerException extends RuntimeException {
    public ConsumerException(String message, Exception ex) {
        super(message,ex);
    }
}
