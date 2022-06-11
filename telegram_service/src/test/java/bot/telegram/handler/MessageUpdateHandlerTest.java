package bot.telegram.handler;

import bot.entity.User;
import bot.repository.UserService;
import bot.telegram.state.AvailableCommands;
import bot.telegram.state.BotCommand;
import bot.telegram.state.CommandProcessor;
import bot.telegram.ui.keyboard.MainMenuKeyboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.piapi.core.InvestApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MessageUpdateHandlerTest {

    @Mock
    UserService service;
    @Mock
    MainMenuKeyboard menu;
    @Mock
    CommandProcessor controller;

    final String chatId = "1111";
    final User userWithDefaultCommand = new User().setId(chatId).setCommand(BotCommand.DEFAULT);

    @Mock
    Message message;

    private MessageUpdateHandler handler;

    @BeforeEach
    public void init() {
        Mockito.mockStatic(AvailableCommands.class);
        handler = new MessageUpdateHandler(menu, controller, service);

    }


    @Test
    void shouldCallProcessNeededProcessor() {
        when(service.getUserOrCreateNewUserByChatId(chatId)).thenReturn(userWithDefaultCommand);
        when(AvailableCommands.findCommand(any())).thenReturn(BotCommand.HELP);
        when(message.getChatId()).thenReturn(Long.valueOf(chatId));

        BotCommand actual = handler.findCommandByUserMessage(message);


        assertEquals(BotCommand.HELP, actual);

    }


    @Test
    void shouldReturnUnrecognizedMessage() {


        when(service.getUserOrCreateNewUserByChatId(chatId)).thenReturn(userWithDefaultCommand);
        when(AvailableCommands.findCommand(any())).thenReturn(null);
        when(message.getChatId()).thenReturn(Long.valueOf(chatId));

        BotCommand actual = handler.findCommandByUserMessage(message);

        assertEquals(BotCommand.UNRECOGNIZED, actual);
        verify(service).saveCondition(userWithDefaultCommand);

    }
}