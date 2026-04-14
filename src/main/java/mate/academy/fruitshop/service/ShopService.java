package mate.academy.fruitshop.service;

import java.util.List;
import java.util.Map;
import mate.academy.fruitshop.model.FruitTransaction;

public interface ShopService {
    void process(List<FruitTransaction> transactions);

    Map<String, Integer> getStorage();
}
