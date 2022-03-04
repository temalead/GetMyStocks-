package bot.exception;

import bot.tinkoff.utils.NotFoundShareMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.CompletionException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShareNotFoundException extends CompletionException {
    public ShareNotFoundException(String message) {
        super(NotFoundShareMessageBuilder.createMsgError(message));
    }
}
