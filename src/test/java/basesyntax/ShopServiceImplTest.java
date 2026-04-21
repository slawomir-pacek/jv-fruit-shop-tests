package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void shouldProcessBalanceOperation() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        FruitTransaction tx = new FruitTransaction();
        tx.setOperation(FruitTransaction.Operation.BALANCE);
        tx.setFruit("apple");
        tx.setQuantity(10);

        ShopServiceImpl service = new ShopServiceImpl(new OperationStrategyImpl(handlers));
        service.process(List.of(tx));

        assertEquals(10, service.getStorage().get("apple"));
    }

    @Test
    void shouldProcessMultipleTransactions() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        FruitTransaction tx1 = new FruitTransaction();
        tx1.setOperation(FruitTransaction.Operation.BALANCE);
        tx1.setFruit("apple");
        tx1.setQuantity(10);

        FruitTransaction tx2 = new FruitTransaction();
        tx2.setOperation(FruitTransaction.Operation.BALANCE);
        tx2.setFruit("banana");
        tx2.setQuantity(5);

        ShopServiceImpl service = new ShopServiceImpl(new OperationStrategyImpl(handlers));
        service.process(List.of(tx1, tx2));

        assertEquals(10, service.getStorage().get("apple"));
        assertEquals(5, service.getStorage().get("banana"));
    }

    @Test
    void shouldProcessAllTypesOfOperations() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        FruitTransaction t1 = new FruitTransaction();
        t1.setOperation(FruitTransaction.Operation.BALANCE);
        t1.setFruit("apple");
        t1.setQuantity(20);

        FruitTransaction t2 = new FruitTransaction();
        t2.setOperation(FruitTransaction.Operation.SUPPLY);
        t2.setFruit("apple");
        t2.setQuantity(5);

        FruitTransaction t3 = new FruitTransaction();
        t3.setOperation(FruitTransaction.Operation.PURCHASE);
        t3.setFruit("apple");
        t3.setQuantity(3);

        FruitTransaction t4 = new FruitTransaction();
        t4.setOperation(FruitTransaction.Operation.RETURN);
        t4.setFruit("apple");
        t4.setQuantity(2);

        ShopServiceImpl service = new ShopServiceImpl(new OperationStrategyImpl(handlers));
        service.process(List.of(t1, t2, t3, t4));

        assertEquals(24, service.getStorage().get("apple"));
    }

    // 🔥 EDGE CASES (to podnosi Jacoco coverage)

    @Test
    void shouldHandleEmptyTransactions() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        ShopServiceImpl service = new ShopServiceImpl(new OperationStrategyImpl(handlers));

        service.process(List.of());

        assertEquals(0, service.getStorage().size());
    }
}
