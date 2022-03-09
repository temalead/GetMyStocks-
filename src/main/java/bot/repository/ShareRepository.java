package bot.repository;

import bot.entity.MyShare;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends CrudRepository<MyShare, String> {

}
