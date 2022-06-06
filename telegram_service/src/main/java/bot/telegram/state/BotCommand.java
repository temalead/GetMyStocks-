package bot.telegram.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum BotCommand {
    BACK,
    DELETE_PORTFOLIO,
    FOUND_BOND,
    FOUND_SHARE,
    HELP,
    GET_PORTFOLIO,
    START,
    HINT,
    MAKE_PORTFOLIO,
    DEFAULT,
    PORTFOLIO,
    UNRECOGNIZED,
    UPDATE_PORTFOLIO,
    FIND_BOND,
    FIND_SHARE,
    WISH_MAKE_PORTFOLIO;

}
