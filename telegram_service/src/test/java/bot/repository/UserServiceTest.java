package bot.repository;

import bot.entity.User;
import bot.telegram.state.BotCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    UserService service;
    @BeforeEach
    void init(){
        service=new UserService(repository);
    }

    @Test
    void shouldReturnDefaultUser() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        User user = service.getUserOrCreateNewUserByChatId(any());

        verify(repository).save(user);

        assertEquals(BotCommand.DEFAULT,user.getCommand());

    }
}