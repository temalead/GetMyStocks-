package stock_service.service;

import org.springframework.data.repository.CrudRepository;
import stock_service.entity.MyShare;

public interface ShareRepository extends CrudRepository<MyShare, String> {

}
