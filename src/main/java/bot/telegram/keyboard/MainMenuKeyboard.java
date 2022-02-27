package bot.telegram.keyboard;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboard(){
        KeyboardRow row=new KeyboardRow();
        row.add(ButtonMenuEnum.GET_BOND.getButtonName());
        row.add(ButtonMenuEnum.GET_SHARE.getButtonName());

        KeyboardRow row1=new KeyboardRow();
        row1.add(ButtonMenuEnum.GET_LIST.getButtonName());
        row1.add(ButtonMenuEnum.SET_LIST.getButtonName());

        KeyboardRow row2=new KeyboardRow();
        row2.add(ButtonMenuEnum.UPDATE_LIST.getButtonName());
        row2.add(ButtonMenuEnum.HELP.getButtonName());

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row);
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
}