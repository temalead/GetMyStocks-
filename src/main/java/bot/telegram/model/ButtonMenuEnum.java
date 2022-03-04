package bot.telegram.model;

public enum ButtonMenuEnum {
    GET_LIST("Get portfolio"),
    SET_LIST("Make portfolio"),
    UPDATE_LIST("Update portfolio"),
    HELP("Help me!"),
    GET_SHARE("Get share by ticker"),
    GET_BOND("Get bond by figi"),
    PORTFOLIO("My portfolio");

    private final String buttonName;

    ButtonMenuEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName(){
        return this.buttonName;
    }
}
