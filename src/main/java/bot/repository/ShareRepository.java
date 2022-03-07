package bot.repository;

import bot.domain.MyShare;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends CrudRepository<MyShare, String> {

}
