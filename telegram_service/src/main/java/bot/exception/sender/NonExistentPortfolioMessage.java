package bot.exception.sender;

public class NonExistentPortfolioMessage {
    public static String createMessageError() {
        return "You don`t have portfolio yet! " + "\n" +
                "To create portfolio, tap make portfolio button";
    }
}
