package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserRepository;
import tech.wg.tools.MockGrammar;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    public static final String ENCRYPTED_PASS = "encryptedPass";


    @InjectMocks
    LoginService service;

    @Mock
    UserRepository userRepository;

    @Mock
    Encryption encryption;

    @Mock
    GameMenu gameMenu;


    @Spy
    MockGrammar grammar;

    @Test
    void testEnterCorrectLogin() {
        grammar.initWithInput("login\n0\n0");
        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", ENCRYPTED_PASS)));
        service.authorization();
        Assertions.assertEquals("Введите логин или 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или 0, чтобы вернуться в предыдущее меню\n" +
                "Введите логин или 0, чтобы вернуться в предыдущее меню", grammar.getOut(), "вывод непонятем высшим силамом");

    }

    @Test
    void testEnterIncorrectLogin() {
        grammar.initWithInput("login\n0");
        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.empty());
        service.authorization();
        Assertions.assertEquals("Введите логин или 0, чтобы вернуться в предыдущее меню\n" +
                "Такого логина не существует. Пожалуйста, попробуйте еще раз\n" +
                "Введите логин или 0, чтобы вернуться в предыдущее меню", grammar.getOut(), "вывод непонятем высшим силамом");
    }

    @Test
    void testCorrectPass() {
        grammar.initWithInput("login\npass");
        Mockito.when(encryption.action("pass"))
                .thenReturn(ENCRYPTED_PASS);
        Mockito.when(userRepository.getUser("login"))
                .thenReturn(Optional.of(new UserEntity("login", ENCRYPTED_PASS)));
        service.authorization();
        Assertions.assertEquals("Введите логин или 0, чтобы вернуться в предыдущее меню\n" +
                        "Введите пароль, или 0, чтобы вернуться в предыдущее меню"
                , grammar.getOut(), "Не правильный вывод на консоль");
    }

    @Test
    void testIncorrectPass() {
        grammar.initWithInput("login\nincorrectPass\n0\n0");
        Mockito.when(encryption.action("incorrectPass"))
                .thenReturn(ENCRYPTED_PASS);
        Mockito.when(userRepository.getUser("login"))
                .thenReturn(Optional.of(new UserEntity("login", "pass")));
        service.authorization();
        Mockito.verify(userRepository, Mockito.times(1)).getUser("login");
        Assertions.assertEquals("Введите логин или 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или 0, чтобы вернуться в предыдущее меню\n" +
                "Не верный пароль! Попробуйте ещё раз.\n" +
                "Введите пароль, или 0, чтобы вернуться в предыдущее меню\n" +
                "Введите логин или 0, чтобы вернуться в предыдущее меню", grammar.getOut(), "не верный вывод на экран кинотеатра аврора");
    }
}