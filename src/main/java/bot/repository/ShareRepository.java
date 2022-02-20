package bot.repository;

import bot.domain.ShareDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends CrudRepository<ShareDto, String> {

}
