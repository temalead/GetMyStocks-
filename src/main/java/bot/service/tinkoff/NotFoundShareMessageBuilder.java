package bot.service.tinkoff;

public abstract class NotFoundShareMessageBuilder {
    public static String createMsgError(String ticker) {
        return String.format( "Акция с тикером %s не существует!", ticker);
    }
}
