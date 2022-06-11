package bot.repository;

import bot.entity.MyBond;
import org.springframework.data.repository.CrudRepository;

public interface BondRepository extends CrudRepository<MyBond, String> {
}
