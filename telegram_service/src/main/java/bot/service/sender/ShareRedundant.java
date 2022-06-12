package bot.service.sender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareRedundant {
    private String message;

    public static String getKafkaMessage() {
        return null;
    }
}
