package bot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundStockException extends Exception{
    public NotFoundStockException(String message) {
        super("Stock with ticket "+message+ " not exist");
    }
}
