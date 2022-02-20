package bot.exception;

import bot.service.tinkoff.utils.NotFoundShareMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundShareException extends RuntimeException{
    public NotFoundShareException(String message) {
        super(NotFoundShareMessageBuilder.createMsgError(message));
    }
}
