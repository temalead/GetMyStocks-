package stock_service.repository;

import org.springframework.data.repository.CrudRepository;
import stock_service.entity.MyShare;

public interface ShareRepository extends CrudRepository<MyShare, String> {

}
