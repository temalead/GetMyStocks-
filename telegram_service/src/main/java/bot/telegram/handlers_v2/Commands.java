package bot.telegram.handlers_v2;

import bot.telegram.buttons.ButtonMenuEnum;

import java.util.Arrays;
import java.util.List;

public enum Commands {
    NONE(List.of()),
    START(List.of("/start")),
    HELP(List.of(ButtonMenuEnum.HELP.getButtonName())),
    GET_SHARE(List.of(ButtonMenuEnum.GET_SHARE.getButtonName())),
    GET_BOND(List.of(ButtonMenuEnum.GET_BOND.getButtonName())),
    GET_PORTFOLIO(List.of(ButtonMenuEnum.PORTFOLIO.getButtonName(),
            ButtonMenuEnum.GET_PORTFOLIO.getButtonName(),
            ButtonMenuEnum.UPDATE_PORTFOLIO.getButtonName(),
            ButtonMenuEnum.MAKE_PORTFOLIO.getButtonName(),
            ButtonMenuEnum.DELETE_PORTFOLIO.getButtonName()));

    private List<String> commands;

    Commands(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public Commands getByCommand(String command){
        return Arrays.stream(values()).filter(it -> it.getCommands().contains(command)).findFirst().orElse(NONE);
    }
}
