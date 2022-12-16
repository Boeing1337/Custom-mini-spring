package tech.wg.servise.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CharactersLoginCheckerTest {

    private final CharactersLoginChecker checker = new CharactersLoginChecker();

    @Test
    void testIfCheckTrueIfLoginHasRussianLetters() {
        Assertions.assertTrue(checker.check("её"), "Чекер не пропустил русские буквы");
    }

    @Test
    void testIfCheckTrueIfLoginHasEnglishLetters() {
        Assertions.assertTrue(checker.check("Al"), "Чекер не пропустил английские буквы");
    }

    @Test
    void testIfCheckTrueIfLoginHasNumbers() {
        Assertions.assertTrue(checker.check("09"), "Чекер не пропустил цифры");
    }

    @Test
    void testIfCheckTrueIfLoginHasUnderscore() {
        Assertions.assertTrue(checker.check("___"), "Чекер не пропустил английские буквы");
    }

    @Test
    void testIfCheckTrueIfLoginCorrect() {
        Assertions.assertTrue(checker.check("aAёЁ10_"), "Чекер не пропустил комбинацию из всех типов символов");
    }

    @Test
    void testIfCheckFalseIfLoginIsNotCorrect() {
        String incorrectSymbols = ";'";
        Assertions.assertFalse(
                checker.check(incorrectSymbols)
                , "Чекер пропустил комбинацию из недопустимых символов " + incorrectSymbols
        );
    }

    @Test
    void testIfErrorHasMessage() {
        Assertions.assertNotNull(checker.getError());
    }
}