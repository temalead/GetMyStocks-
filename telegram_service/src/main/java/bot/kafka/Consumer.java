package bot.kafka;

import bot.entity.Security;

public interface Consumer {

    Security getSecurityFromKafka(String message);
}
