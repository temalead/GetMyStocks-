package stock_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import stock_service.entity.Asset;
import stock_service.exception.sender.NotFoundMessageBuilder;

import java.util.concurrent.CompletionException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShareNotFoundException extends CompletionException {
    public ShareNotFoundException(String message) {
        super(NotFoundMessageBuilder.createMessageError(message, Asset.SHARE));
    }
}
