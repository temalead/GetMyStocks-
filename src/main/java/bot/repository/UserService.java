package bot.repository;

import bot.domain.User;
import bot.telegram.state.BotState;
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

    public User initializeUser(String chatId) {
        Optional<User> user = repository.findById(chatId);
        if (user.isPresent()){
            return user.get();
        }
        else {
            log.info("Creating new user");
            User newUser = new User().setState(BotState.NONE).setId(chatId);
            repository.save(newUser);
            return newUser;
        }
    }

    public void saveCondition(User user){
        repository.save(user);
    }
}
