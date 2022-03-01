package bot.domain;

import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import javax.persistence.Id;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Component
@RedisHash("User")
@Accessors(chain = true)
public class User {
    @Id
    String id;
    BotState state;


}
