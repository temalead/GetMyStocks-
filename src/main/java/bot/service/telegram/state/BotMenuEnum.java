package bot.service.telegram.state;

public enum BotMenuEnum {
    FIND_BOND,
    FIND_SHARE,
    GET_LIST,
    MAKE_LIST,
    SHOW_MAIN_MENU,
    SHOW_HELP("HI! I'll help you soon...");

    BotMenuEnum(String message) {
        this.message = message;
    }

    BotMenuEnum() {
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
