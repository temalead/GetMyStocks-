package bot.exception;

import bot.exception.sender.Assets;
import bot.exception.sender.NotFoundMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BondNotFoundException extends RuntimeException {
    public BondNotFoundException(String name) {
        super(NotFoundMessageBuilder.createMessageError(name, Assets.BOND));
    }
}
