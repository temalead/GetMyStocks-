package bot.telegram.keyboard;


import bot.telegram.buttons.ButtonMenuEnum;
import bot.telegram.utils.KeyboardCreator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class MainMenuKeyboard implements Keyboard{

    @Override
    public ReplyKeyboardMarkup getKeyboard(){
        KeyboardRow row=new KeyboardRow();
        row.add(ButtonMenuEnum.GET_BOND.getButtonName());
        row.add(ButtonMenuEnum.GET_SHARE.getButtonName());

        KeyboardRow row1=new KeyboardRow();
        row1.add(ButtonMenuEnum.PORTFOLIO.getButtonName());
        row1.add(ButtonMenuEnum.HELP.getButtonName());


        ReplyKeyboardMarkup keyboard = KeyboardCreator.createKeyboardByRows(List.of(row, row1));

        return keyboard;
    }
}
