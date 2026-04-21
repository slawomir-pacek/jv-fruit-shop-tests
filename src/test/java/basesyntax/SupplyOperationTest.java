package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_QUANTITY = 10;
    private static final int SUPPLY_QUANTITY = 5;

    private SupplyOperation operation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        operation = new SupplyOperation();
        storage = new HashMap<>();
    }

    @Test
    void process_fruitExistsInStorage_shouldIncreaseStock() {
        storage.put(APPLE, INITIAL_QUANTITY);
        FruitTransaction transaction = createTransaction(APPLE, SUPPLY_QUANTITY);

        operation.process(transaction, storage);

        assertEquals(15, storage.get(APPLE));
    }

    @Test
    void process_fruitAbsentInStorage_shouldAddFruitToStorage() {
        FruitTransaction transaction = createTransaction(APPLE, SUPPLY_QUANTITY);

        operation.process(transaction, storage);

        assertEquals(SUPPLY_QUANTITY, storage.get(APPLE));
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
