package stock_service.exception;

public class ValidateDataException extends RuntimeException{
    public ValidateDataException(String message) {
        super(message);
    }
}
