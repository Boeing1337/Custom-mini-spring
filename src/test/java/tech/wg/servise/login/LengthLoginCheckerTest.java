package tech.wg.servise.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.extention.ExtendWIthTech;
import tech.ioc.annotations.InjectProperty;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWIthTech
class LengthLoginCheckerTest {

    private final LengthLoginChecker checker = new LengthLoginChecker();

    @InjectProperty
    private int minLoginLength;

    @InjectProperty
    private int maxLoginLength;

    @Test
    void checkIfMinLoginLengthMoreThan1() {
        assertTrue(minLoginLength > 0, "Минимальная настройка длинны логина должна быть не меньше 1");
    }

    @Test
    void checkIfMaxLoginLengthLessThan15() {
        assertTrue(maxLoginLength < 16, "Максимальная настройка длинны логина должна быть не больше 15");
    }

    @Test
    void checkFalseIfLoginLengthMoreThanMax() {
        assertFalse(checker.check("S".repeat(maxLoginLength + 1)));
    }

    @Test
    void checkFalseIfLoginLengthLessThanMin() {
        assertFalse(checker.check("S".repeat(minLoginLength - 1)));
    }

    @Test
    void checkTrueIfLoginLengthCorrect() {
        assertTrue(checker.check("S".repeat(minLoginLength)));
        assertTrue(checker.check("S".repeat(maxLoginLength)));
    }

    @Test
    void testIfErrorHasMessage() {
        Assertions.assertNotNull(checker.getError());
    }

}