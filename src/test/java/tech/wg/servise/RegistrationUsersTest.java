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
import tech.wg.dao.UserRepository;
import tech.wg.tools.MockGrammar;

@ExtendWith(MockitoExtension.class)
class RegistrationUsersTest {
    @InjectMocks
    RegistrationUsers service;
    @Spy
    MockGrammar mockGrammar;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void soglasovanie() {
        Mockito.when(userRepository.createUser("login", "pass")).thenReturn(true);

        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
    }

    @Test
    void registrationUser() {
        mockGrammar.setInputContent("login\n0\nlogin\npass\npass");
        service.registrationUser();
        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Вы успешно зарегистрировались в Диминой игре!", mockGrammar.getOut());

    }

    @Test
    void registrationUser2() {
        mockGrammar.setInputContent("login\n0\nlogin\npass\n0\n0\nlogin\npass\npass");
        service.registrationUser();
        Assertions.assertEquals("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите логин или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню\n" +
                "Вы успешно зарегистрировались в Диминой игре!", mockGrammar.getOut());
    }
}






