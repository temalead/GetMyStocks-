package bot.telegram.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

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
    WANNA_GET_SHARE, WANNA_MAKE_PORTFOLIO;
}
