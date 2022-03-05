package bot.exception.sender;

import java.util.Locale;

public class NotFoundMessageBuilder {
    
    public static String createMessageError(String name, Assets asset) {
        return String.format( "Error. %s %s not found!\n" +
                "Please, try again", asset.name().toLowerCase(Locale.ROOT),name);
    }
}
