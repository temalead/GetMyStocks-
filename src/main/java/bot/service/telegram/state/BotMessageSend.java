package bot.service.telegram.state;

public enum BotMessageSend {
    HELP_MESSAGE("I`ll help you soon..."),
    NOT_RECOGNIZE("I don`t recognize you. Please use help menu"),
    SEND_SHARE("Sending share...");

    private final String message;

    BotMessageSend(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
