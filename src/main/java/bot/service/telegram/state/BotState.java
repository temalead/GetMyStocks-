package bot.service.telegram.state;

public enum BotState {
    FIND_BOND,
    FIND_SHARE,
    GET_LIST,
    MAKE_LIST,
    SHOW_MAIN_MENU,
    SHOW_HELP("HI! I'll help you soon...");

    BotState(String message) {
        this.message = message;
    }

    BotState() {
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
