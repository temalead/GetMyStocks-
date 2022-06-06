package bot.telegram.ui.keyboard;

import bot.telegram.ui.buttons.ButtonMenuEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class PortfolioMenuKeyBoard implements Keyboard {

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        KeyboardRow row = new KeyboardRow();
        row.addAll(List.of(ButtonMenuEnum.UPDATE_PORTFOLIO.getButtonName(),
                ButtonMenuEnum.GET_PORTFOLIO.getButtonName()));
        KeyboardRow row1 = new KeyboardRow();
        row1.addAll(List.of(ButtonMenuEnum.MAKE_PORTFOLIO.getButtonName(),
                ButtonMenuEnum.DELETE_PORTFOLIO.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(ButtonMenuEnum.BACK.getButtonName());

        return KeyboardCreator.createKeyboardByRows(List.of(row1, row,row2));

    }
}
