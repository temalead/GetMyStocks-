package bot.exception.sender;

import java.util.Locale;

public class NotFoundMessageBuilder {
    
    public static String createMessageError(String name, Asset asset) {
        return String.format( "Error. %s %s not found!\n" +
                "Please, try again", asset.name().toLowerCase(Locale.ROOT),name);
    }

    public static String createPortfolioError( ) {
        return "Error. Portfolio not found";
    }
}
