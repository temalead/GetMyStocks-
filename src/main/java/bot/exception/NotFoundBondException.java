package bot.exception;

public class NotFoundBondException extends RuntimeException {
    public NotFoundBondException(String ticker) {
        super(ticker);
    }
}
