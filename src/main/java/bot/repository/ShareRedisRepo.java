package bot.repository;

import bot.domain.dto.ShareDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;


@Repository
@RequiredArgsConstructor
public class ShareRedisRepo {
    private final Jedis jedis;

    public ShareDto findByTicker(String ticker){
        return new Gson().fromJson(jedis.get(ticker),ShareDto.class);
    }
}
