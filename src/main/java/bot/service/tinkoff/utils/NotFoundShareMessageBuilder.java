package bot.service.tinkoff.utils;

public abstract class NotFoundShareMessageBuilder {
    public static String createMsgError(String ticker) {
        return String.format( "Акция с тикером %s не существует!", ticker);
    }
}
