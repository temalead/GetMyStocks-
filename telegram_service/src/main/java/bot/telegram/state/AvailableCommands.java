package bot.telegram.state;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static bot.telegram.state.BotCommand.BACK;
import static bot.telegram.state.BotCommand.DELETE_PORTFOLIO;
import static bot.telegram.state.BotCommand.FIND_BOND;
import static bot.telegram.state.BotCommand.FIND_SHARE;
import static bot.telegram.state.BotCommand.GET_PORTFOLIO;
import static bot.telegram.state.BotCommand.HELP;
import static bot.telegram.state.BotCommand.PORTFOLIO;
import static bot.telegram.state.BotCommand.START;
import static bot.telegram.state.BotCommand.UPDATE_PORTFOLIO;
import static bot.telegram.state.BotCommand.WISH_MAKE_PORTFOLIO;
import static java.util.Map.entry;


public class AvailableCommands {
    private static final Map<String, BotCommand> commands = Map.ofEntries(
            entry("/start", START),
            entry("Back", BACK),
            entry("Delete portfolio", DELETE_PORTFOLIO),
            entry("Help me!", HELP),
            entry("Get portfolio", GET_PORTFOLIO),
            entry("My portfolio", PORTFOLIO),
            entry("Update portfolio", UPDATE_PORTFOLIO),
            entry("Make portfolio", WISH_MAKE_PORTFOLIO),
            entry("Get bond by name", FIND_BOND),
            entry("Get share by ticker", FIND_SHARE)
    );

    public static BotCommand findCommand(String input) {
        return Optional.of(commands.get(input)).orElse(null);

    }

}
