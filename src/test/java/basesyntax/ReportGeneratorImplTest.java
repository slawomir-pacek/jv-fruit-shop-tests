package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void getReport_shouldReturnHeaderOnly_whenStorageIsEmpty() {
        Map<String, Integer> storage = new HashMap<>();
        ReportGenerator generator = new ReportGeneratorImpl(storage);

        String expected = "fruit,quantity\n";

        assertEquals(expected, generator.getReport());
    }

    @Test
    void getReport_shouldReturnCorrectReport_whenStorageHasData() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);

        ReportGenerator generator = new ReportGeneratorImpl(storage);

        String result = generator.getReport();

        assertEquals(true, result.contains("fruit,quantity\n"));
        assertEquals(true, result.contains("apple,10\n"));
        assertEquals(true, result.contains("banana,5\n"));
    }
}
