package stock_service.exception;

public class PortfolioCompositionValidationException extends RuntimeException{
    public PortfolioCompositionValidationException(String message) {
        super(message);
    }
}
