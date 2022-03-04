package bot.telegram.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public enum BotState {
    NONE,
    HINT,
    WANNA_GET_SHARE,
    WANNA_GET_BOND,
    GET_HELP,
    GET_START_MENU,
    PORTFOLIO,
    MAKE_PORTFOLIO,
    UPDATE_PORTFOLIO,
    DELETE_PORTFOLIO,
    GET_PORTFOLIO,
    FIND_SHARE,
    FIND_BOND,
    UNRECOGNIZED;

}
