package bot.telegram.state;

public enum BotMessageSendHinter {
    HELP_MESSAGE("I`ll help you soon..."),
    NOT_RECOGNIZE("I don`t recognize you. Please use help menu"),
    SEND_SHARE("Please, write share ticker like GAZP or SBER"),
    SEND_BOND("Please, write bond ticker like ");


    private final String message;

    BotMessageSendHinter(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
