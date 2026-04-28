package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private Map<FruitTransaction.Operation,
            OperationHandler> createFullHandlers() {

        Map<FruitTransaction.Operation,
                OperationHandler> handlers = new HashMap<>();

        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        return handlers;
    }

    @Test
    void shouldProcessValidTransaction() {

        FruitTransaction valid = new FruitTransaction();
        valid.setOperation(FruitTransaction.Operation.BALANCE);
        valid.setFruit("apple");
        valid.setQuantity(10);

        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(createFullHandlers())); 
        service.process(List.of(valid));

        assertEquals(10, service.getStorage().get("apple"));
    }

    @Test
    void shouldProcessAllOperations() {
        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(createFullHandlers()));

        service.process(List.of(
                tx(FruitTransaction.Operation.BALANCE, "apple", 20),
                tx(FruitTransaction.Operation.SUPPLY, "apple", 5),
                tx(FruitTransaction.Operation.PURCHASE, "apple", 3),
                tx(FruitTransaction.Operation.RETURN, "apple", 2)
        ));

        assertEquals(24, service.getStorage().get("apple"));
    }

    @Test
    void shouldThrowException_whenStrategyNull() {
        assertThrows(RuntimeException.class,
                () -> new ShopServiceImpl(null));
    }

    @Test
    void shouldThrowException_whenTransactionsNull() {
        ShopServiceImpl service =
                new ShopServiceImpl(new OperationStrategyImpl(createFullHandlers()));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.process(null));

        assertEquals("Transactions cannot be null", ex.getMessage());
    }

    private FruitTransaction tx(FruitTransaction.Operation op,
                                String fruit,
                                int qty) {
        FruitTransaction t = new FruitTransaction();
        t.setOperation(op);
        t.setFruit(fruit);
        t.setQuantity(qty);
        return t;
    }
}
