package bot.entity;

import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Component
@RedisHash("User")
@Accessors(chain = true)
public class User implements Serializable {
    String id;
    BotState state;
    Portfolio portfolio;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
