package stock_service.service;


import org.springframework.data.repository.CrudRepository;
import stock_service.entity.MyBond;

public interface BondRepository extends CrudRepository<MyBond, String> {
}
