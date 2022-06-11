package bot.repository;

import bot.entity.MyShare;
import org.springframework.data.repository.CrudRepository;

public interface ShareRepository extends CrudRepository<MyShare, String> {

}
