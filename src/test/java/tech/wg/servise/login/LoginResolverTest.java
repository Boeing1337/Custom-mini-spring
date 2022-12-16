package tech.wg.servise.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import tech.extention.ExtendWIthTechAndMockito;
import tech.ioc.annotations.InjectProperty;
import tech.wg.tools.MockGrammar;

import java.util.ArrayList;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWIthTechAndMockito
class LoginResolverTest {

    public static final String SOME_ERROR = "some error1";
    @InjectMocks
    private LoginResolver loginResolver;

    @Spy
    private MockGrammar mockGrammar;

    @Spy
    private ArrayList<LoginChecker> loginCheckers = new ArrayList<>();

    @InjectProperty
    private int minLoginLength;

    @InjectProperty
    private int maxLoginLength;

    @BeforeEach
    void setup() {
        loginCheckers.add(new LoginChecker() {
            @Override
            public boolean check(String login) {
                return login.length() > 0;
            }

            @Override
            public String getError() {
                return SOME_ERROR;
            }
        });
    }

    @Test
    void testCorrectLoginResolve() {
        mockGrammar.initWithInput("0\n");
        Optional<String> result = loginResolver.resolve();
        assertAll(
                () -> assertTrue(
                        result.isPresent(),
                        "При корректном логине должен вернуться НЕ пустой ответ"),
                () -> assertEquals(
                        format(
                                "В логине допустимы только буквы, цифры и знак _ и длинной от %s до %s знаков"
                                , minLoginLength
                                , maxLoginLength)
                        , mockGrammar.getOut()
                ));
    }

    @Test
    void testInCorrectLoginResolve() {
        mockGrammar.initWithInput("\n");
        Optional<String> result = loginResolver.resolve();
        assertAll(
                () -> assertFalse(
                        result.isPresent(),
                        "При НЕкорректном логине должен вернуться пустой ответ"),
                () -> assertEquals(
                        format(
                                "В логине допустимы только буквы, цифры и знак _ и длинной от %s до %s знаков\n"
                                , minLoginLength
                                , maxLoginLength) +
                                SOME_ERROR
                        , mockGrammar.getOut()
                ));
    }
}