package stock_service.exception.sender;

import java.util.Locale;

public class NotFoundMessageBuilder {
    
    public static String createMessageError(String name, Asset asset) {
        return String.format( "Error. %s %s not found!\n" +
                "Please, try again", asset.name().toLowerCase(Locale.ROOT),name);
    }
}
