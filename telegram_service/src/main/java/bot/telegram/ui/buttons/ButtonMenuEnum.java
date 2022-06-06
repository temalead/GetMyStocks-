package bot.telegram.ui.buttons;

public enum ButtonMenuEnum {
    GET_PORTFOLIO("Get portfolio"),
    MAKE_PORTFOLIO("Make portfolio"),
    UPDATE_PORTFOLIO("Update portfolio"),
    DELETE_PORTFOLIO("Delete portfolio"),
    HELP("Help me!"),
    GET_SHARE("Get share by ticker"),
    GET_BOND("Get bond by name"),
    BACK("Back"),
    PORTFOLIO("My portfolio");

    private final String buttonName;

    ButtonMenuEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName(){
        return this.buttonName;
    }
}
