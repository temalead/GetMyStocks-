package bot.telegram.state;

import bot.telegram.buttons.ButtonMenuEnum;

import java.util.Arrays;
import java.util.List;

public enum PortfolioBotState {
    PORTFOLIO(List.of(ButtonMenuEnum.MAKE_PORTFOLIO.getButtonName(),
            ButtonMenuEnum.GET_PORTFOLIO.getButtonName(),
            ButtonMenuEnum.DELETE_PORTFOLIO.getButtonName(),
            ButtonMenuEnum.UPDATE_PORTFOLIO.getButtonName())),

    MAIN_MENU(List.of());
    List<String> commands;

    PortfolioBotState(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public PortfolioBotState getCommand(String command) {
        return Arrays.stream(values()).filter(it -> it.getCommands().contains(command)).findFirst().orElse(null);
    }
}
