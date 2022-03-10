package bot.telegram.buttons;

public enum BotMessageSend {
    START_MESSAGE("I glad to see you! To see advanded info, please tap help"),
    HELP_MESSAGE("I`ll help you soon..."),
    UNRECOGNIZED_MESSAGE("I don`t recognize you. Please use help menu"),
    SHARE_ADVICE_MESSAGE("Please, write share ticker like GAZP or SBER"),
    BOND_ADVICE_MESSAGE("Please, write bond ticker like ОФЗ 24019"),
    MAKE_PORTFOLIO_ADVICE("Please, write message like GAZP-10, ОФЗ 26209-10\n"+
            "where parameter before - is name of security and number is lot quantity"),
    PORTFOLIO_ADVICE("Please, use commands below to work with your portfolio");


    private final String message;

    BotMessageSend(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
