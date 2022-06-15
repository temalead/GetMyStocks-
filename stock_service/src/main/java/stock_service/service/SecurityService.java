package stock_service.service;

import stock_service.entity.Security;

public interface SecurityService {

    Security getAssetFromTinkoffByTicker(String name);

}
