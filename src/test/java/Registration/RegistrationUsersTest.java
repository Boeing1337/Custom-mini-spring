package Registration;

import dao.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class RegistrationUsersTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @InjectMocks
    RegistrationUsers service;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void soglasovanie() {
        System.setOut(new PrintStream(outContent));
        Mockito.when(userRepository.createUser("login")).thenReturn(true);
        Mockito.when(userRepository.createPass("login", "pass")).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
    }

    @Test
    void registrationUser() {
        System.setIn(new ByteArrayInputStream("login\n0\nlogin\npass\npass".getBytes()));
        service.registrationUser();
        System.setOut(originalOut);
//        System.out.println(outContent);
        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Вы успешно зарегистрировались в Диминой игре!\r\n", outContent.toString());

    }

    @Test
    void registrationUser2() {
        System.setIn(new ByteArrayInputStream("login\n0\nlogin\npass\n0\n0\nlogin\npass\npass".getBytes()));
        service.registrationUser();
        System.setOut(originalOut);
//        System.out.println(outContent);
        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\r\n" +
                "Вы успешно зарегистрировались в Диминой игре!\r\n", outContent.toString());
    }
}






