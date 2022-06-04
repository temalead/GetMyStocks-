package stock_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import stock_service.entity.Asset;
import stock_service.exception.sender.NotFoundMessageBuilder;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BondNotFoundException extends RuntimeException {
    public BondNotFoundException(String name) {
        super(NotFoundMessageBuilder.createMessageError(name, Asset.BOND));
    }
}
