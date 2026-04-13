package core.basesyntax.service.impl;

import java.util.HashMap;
import java.util.Map;
import core.basesyntax.model.FruitTransaction;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;

 class PurchaseOperationTest {

    private final PurchaseOperation operation = new PurchaseOperation();

    @Test
    void shouldDecreaseStock_whenEnoughFruit() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 20);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(5);

        operation.process(tx, storage);

        Assertions.assertEquals(15, storage.get("banana"));
    }

    @Test
    void shouldThrowException_whenNotEnoughStock() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 5);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(10);

        Assertions.assertThrows(RuntimeException.class,
                () -> operation.process(tx, storage));
    }
}
