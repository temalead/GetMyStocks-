package bot.telegram.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum BotState {
    BACK,
    DELETE_PORTFOLIO,
    FIND_BOND,
    FIND_SHARE,
    GET_HELP,
    GET_PORTFOLIO,
    GET_START_MENU,
    HINT,
    MAKE_PORTFOLIO,
    NONE,
    PORTFOLIO,
    UNRECOGNIZED,
    UPDATE_PORTFOLIO,
    WANNA_GET_BOND,
    WANNA_GET_SHARE, WAIT_MAKE_PORTFOLIO;
    /*HINT(List.of("/start","Help me!")),
    PORTFOLIO(List.of("Get portfolio","Make portfolio","Update portfolio","Delete portfolio")),
    WANNA_GET_SHARE(List.of("Get share by ticker")),
    WANNA_GET_BOND(List.of(""))

    private List<String> commands;

    BotState(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }*/
}
