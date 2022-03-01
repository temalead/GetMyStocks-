package bot.tinkoff.utils;

public abstract class NotFoundShareMessageBuilder {
    public static String createMsgError(String ticker) {
        return String.format( "Share with ticker %s not found!\n" +
                "Please, try again", ticker);
    }
}
