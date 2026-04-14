package mate.academy.fruitshop.service;

import java.util.Map;
import mate.academy.fruitshop.model.FruitTransaction;

public interface OperationHandler {
    void process(FruitTransaction tx, Map<String, Integer> storage);
}
