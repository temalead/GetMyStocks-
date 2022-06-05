package stock_service.entity;

import lombok.Value;

@Value
public class Message {

    String chatId;
    Security securityResult;
}
