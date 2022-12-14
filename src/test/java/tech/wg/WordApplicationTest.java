package tech.wg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class WordApplicationTest {

    @Test
    void testBoot() {
        assertDoesNotThrow(() -> WordApplication.main(new String[]{"test"}), "Приложение должно запускаться");
    }

}