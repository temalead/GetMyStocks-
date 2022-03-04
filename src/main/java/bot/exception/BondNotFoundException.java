package bot.exception;

public class BondNotFoundException extends RuntimeException {
    public BondNotFoundException(String ticker) {
        super(ticker);
    }
}
