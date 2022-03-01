package bot.repository;

import bot.domain.User;
import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserService {
    UserRepository repository;

    public User initializeUser(String chatId) {
        Optional<User> user = repository.findById(chatId);
        if (user.isPresent()){
            return user.get();
        }
        else {
            User newUser = new User().setState(BotState.NONE).setChatId(chatId);
            repository.save(newUser);
            return newUser;
        }
    }

    public User saveCondition(User user){
        return repository.save(user);
    }
}
