package bot.repository;

import bot.domain.User;
import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    UserRepository repository;

    public User getUserOrCreateNewUserByChatId(String chatId) {
        return repository.findById(chatId).orElse(repository.save(new User().setId(chatId).setState(BotState.NONE)));
    }

    public void saveCondition(User user){
        repository.save(user);
    }
}
