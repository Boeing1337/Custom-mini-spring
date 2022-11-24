package Servise;

import dao.UserEntity;
import dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LoginSeviseTest {

    @InjectMocks
    LoginSevise service;

    @Mock
    UserRepository userRepository;

    @Test
    void enterYourLogin() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", "pass")));
        System.setIn(new ByteArrayInputStream("login\ngfhgfhg\njhghjg\n0\n0".getBytes()));
        service.authorization();
        System.setOut(originalOut);
        System.out.println(outContent);
    }

    @Test
    void enterYourLogin1() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", "pass")));
        System.setIn(new ByteArrayInputStream("login\npass".getBytes()));
        service.authorization();
        System.setOut(originalOut);
        System.out.println(outContent);
    }

    @Test
    void enterYourLogin2() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        Mockito.when(userRepository.getUser("login")).thenReturn(Optional.of(new UserEntity("login", "pass")));
        Mockito.when(userRepository.getUser("pizduk")).thenReturn(Optional.empty());
        System.setIn(new ByteArrayInputStream("pizduk\nlogin\n0\nlogin\npazz\npass".getBytes()));
        service.authorization();
        System.setOut(originalOut);
        System.out.println(outContent);
    }


}