package bot.exception;

import bot.exception.sender.Asset;
import bot.exception.sender.NotFoundMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.CompletionException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShareNotFoundException extends CompletionException {
    public ShareNotFoundException(String message) {
        super(NotFoundMessageBuilder.createMessageError(message, Asset.SHARE));
    }
}
