package tech.wg.servise;

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

    @InjectMocks
    LoginService service;

    @Mock
    UserRepository userRepository;

    @Spy
    MockGrammar grammar;

    @Test
    void enterYourLogin() {
        grammar.initWithInput("login\ngfhgfhg\njhghjg\n0\n0");
        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", "pass")));
        service.authorization();
    }

    @Test
    void enterYourLogin1() {
        grammar.initWithInput("login\npass");
        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", "pass")));
        service.authorization();
    }

    @Test
    void enterYourLogin2() {
        grammar.initWithInput("pizduk\nlogin\n0\nlogin\npazz\npass");
        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", "pass")));
        Mockito.when(userRepository.getUser("pizduk")).thenReturn(Optional.empty());
        service.authorization();

    }


}