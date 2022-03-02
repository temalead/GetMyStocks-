package bot.telegram.model;

public enum BotMessageSend {
    START_MESSAGE("I glad to see you! To see advanded info, please tap help"),
    HELP_MESSAGE("I`ll help you soon..."),
    UNRECOGNIZED_MESSAGE("I don`t recognize you. Please use help menu"),
    SHARE_ADVICE_MESSAGE("Please, write share ticker like GAZP or SBER"),
    BOND_ADVICE_MESSAGE("Please, write bond ticker like ");


    private final String message;

    BotMessageSend(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
