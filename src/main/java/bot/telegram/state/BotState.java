package bot.telegram.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@Getter
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public enum BotState {
    NONE,
    HINT,
    WANNA_GET_SHARE,
    WANNA_GET_BOND,
    GET_HELP,
    GET_START_MENU,
    FIND_SHARE,
    FIND_BOND,
    BACK,
    UNRECOGNIZED,
    PORTFOLIO,
    MAKE_PORTFOLIO,
    UPDATE_PORTFOLIO,
    DELETE_PORTFOLIO,
    GET_PORTFOLIO;
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
