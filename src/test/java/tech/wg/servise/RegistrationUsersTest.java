package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.dao.PlayersScoreRepository;
import tech.wg.dao.UserRepository;
import tech.wg.servise.login.LoginResolver;
import tech.wg.tools.MockGrammar;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RegistrationUsersTest {
    public static final String ENCRYPTED_PASS = "encryptedPass";
    @InjectMocks
    RegistrationUsers service;
    @Spy
    MockGrammar mockGrammar;
    @Mock
    UserRepository userRepository;
    @Mock
    Encryption encryption;
    @Mock
    GameMenu gameMenu;
    @Mock
    PlayersScoreRepository playersScoreRepository;
    @Mock
    private LoginResolver loginResolver;

    @BeforeEach
    void soglasovanie() {
        Mockito.when(encryption.action("pass")).thenReturn(ENCRYPTED_PASS);
    }


    @Test
    void testInputPasswordFalse() {
        Mockito.when(userRepository.createUser("login", ENCRYPTED_PASS)).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
        Mockito.when(loginResolver.resolve()).thenReturn(Optional.of("login"));

        mockGrammar.initWithInput("\npass\npass");

        service.registrationUser();

        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Пароль должен содержать значение\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню", mockGrammar.getOut());
    }

    @Test
    void testInputPasswordFalse2() {
        Mockito.when(userRepository.createUser("login", ENCRYPTED_PASS)).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
        Mockito.when(loginResolver.resolve()).thenReturn(Optional.of("login"));
        mockGrammar.initWithInput("pass\npzass\npass");

        service.registrationUser();

        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню", mockGrammar.getOut());
    }

    @Test
    void testThereIsAlreadySuchUser() {
        Mockito.when(userRepository.createUser("login", ENCRYPTED_PASS)).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("existingLogin")).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
        Mockito.when(loginResolver.resolve())
                .thenReturn(Optional.of("existingLogin"))
                .thenReturn(Optional.of("login"));

        mockGrammar.initWithInput("pass\npass");

        service.registrationUser();

        Assertions.assertEquals(
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Такой логин уже занят. Пожалуйста, попробуйте еще раз\n" +
                        "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню"
                , mockGrammar.getOut());
    }

    @Test
    void registrationUser() {
        Mockito.when(userRepository.createUser("login", ENCRYPTED_PASS)).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
        Mockito.when(loginResolver.resolve()).thenReturn(Optional.of("login"));

        mockGrammar.initWithInput("0\npass\npass");

        service.registrationUser();

        Assertions.assertEquals(
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню"
                , mockGrammar.getOut());
    }

    @Test
    void registrationUser2() {
        Mockito.when(userRepository.createUser("login", ENCRYPTED_PASS)).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
        Mockito.when(loginResolver.resolve()).thenReturn(Optional.of("login"));

        mockGrammar.initWithInput("0\npass\n0\n0\npass\npass");

        service.registrationUser();

        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню"
                , mockGrammar.getOut());
    }
}






