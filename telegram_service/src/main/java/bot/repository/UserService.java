package bot.repository;

import bot.entity.User;
import bot.telegram.state.BotCommand;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    UserRepository repository;

    public User getUserOrCreateNewUserByChatId(String chatId) {
        Optional<User> found = repository.findById(chatId);
        if (found.isPresent()){
            return found.get();
        }
        else {
            log.info("Creating new user..." );
            return new User().setState(BotCommand.NONE).setId(chatId);
        }
    }

    public void saveCondition(User user){
        repository.save(user);
    }
}
