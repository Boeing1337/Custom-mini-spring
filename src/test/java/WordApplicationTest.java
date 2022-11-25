import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class WordApplicationTest {

    @Test
    void testBoot() {
        assertDoesNotThrow(() -> WordApplication.main(null), "Приложение должно запускаться");
    }

}