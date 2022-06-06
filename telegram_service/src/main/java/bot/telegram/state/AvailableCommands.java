package bot.telegram.state;

import java.util.HashMap;
import java.util.Map;

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


public class AvailableCommands {
    private static final Map<String, BotCommand> commands = new HashMap<>();

    public AvailableCommands() {
        commands.put("/start", START);
        commands.put("Back", BACK);
        commands.put("Delete portfolio", DELETE_PORTFOLIO);
        commands.put("Help me!", HELP);
        commands.put("Get portfolio", GET_PORTFOLIO);
        commands.put("My portfolio", PORTFOLIO);
        commands.put("Update portfolio", UPDATE_PORTFOLIO);
        commands.put("Get bond by name", FIND_BOND);
        commands.put("Get share by ticker", FIND_SHARE);
        commands.put("Make portfolio", WISH_MAKE_PORTFOLIO);

    }

    public static BotCommand findCommand(String input) {
        return commands.get(input);
    }

}
