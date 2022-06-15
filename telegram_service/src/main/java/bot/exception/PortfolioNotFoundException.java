package bot.exception;

import bot.exception.sender.Asset;
import bot.exception.sender.NotFoundMessageBuilder;

public class PortfolioNotFoundException extends RuntimeException{
    public PortfolioNotFoundException() {
        super(NotFoundMessageBuilder.createPortfolioError());
    }

}
