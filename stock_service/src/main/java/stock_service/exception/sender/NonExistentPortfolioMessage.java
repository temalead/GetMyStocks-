package stock_service.exception.sender;

public class NonExistentPortfolioMessage {
    public static String createMessageError() {
        return "You don`t have portfolio yet!"+
                "to create portfolio, tap make portfolio button";
    }
}
