package bot.telegram.model;

import bot.telegram.utils.KeyboardCreator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class PortfolioMenuKeyBoard {
    public ReplyKeyboardMarkup getPortfolioKeyboard() {
        KeyboardRow row = new KeyboardRow();
        row.addAll(List.of(ButtonMenuEnum.UPDATE_PORTFOLIO.getButtonName(),
                ButtonMenuEnum.GET_PORTFOLIO.getButtonName()));
        KeyboardRow row1 = new KeyboardRow();
        row1.addAll(List.of(ButtonMenuEnum.SET_PORTFOLIO.getButtonName(),
                ButtonMenuEnum.DELETE_PORTFOLIO.getButtonName()));

        ReplyKeyboardMarkup keyboard = KeyboardCreator.createKeyboardByRows(List.of(row1, row));

        return keyboard;

    }
}
