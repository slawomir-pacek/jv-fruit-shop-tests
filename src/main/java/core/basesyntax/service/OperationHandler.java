package core.basesyntax.service;

import java.util.Map;
import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void process(FruitTransaction tx, Map<String, Integer> storage);
}
