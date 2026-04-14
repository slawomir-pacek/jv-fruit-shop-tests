package mate.academy.fruitshop.service;

import mate.academy.fruitshop.model.FruitTransaction;
import java.util.List;

public interface DataConverter {
    List<FruitTransaction> convertToTransaction(List<String> data);
}
