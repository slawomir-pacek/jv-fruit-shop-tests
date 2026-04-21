package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    private ShopServiceImpl service;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(strategy);
    }

    @Test
    void process_balanceAndPurchaseOperations_shouldUpdateStorageCorrectly() {
        FruitTransaction balance = createTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, 100
        );
        FruitTransaction purchase = createTransaction(
                FruitTransaction.Operation.PURCHASE, BANANA, 30
        );

        service.process(List.of(balance, purchase));

        assertEquals(70, service.getStorage().get(BANANA));
    }

    @Test
    void process_multipleOperations_shouldHandleDifferentFruitsCorrectly() {
        FruitTransaction bananaBalance = createTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, 50
        );
        FruitTransaction bananaSupply = createTransaction(
                FruitTransaction.Operation.SUPPLY, BANANA, 20
        );
        FruitTransaction appleBalance = createTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, 40
        );

        service.process(List.of(
                bananaBalance,
                bananaSupply,
                appleBalance
        ));

        assertEquals(70, service.getStorage().get(BANANA));
        assertEquals(40, service.getStorage().get(APPLE));
    }

    private FruitTransaction createTransaction(FruitTransaction.Operation operation,
                                               String fruit,
                                               int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
