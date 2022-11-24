package Servise;

import Registration.RegistrationUsers;
import dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class RegistrationUsersTest {

    @InjectMocks
    RegistrationUsers service;

    @Mock
    UserRepository userRepository;

    @Test
    void registrationUser() throws IOException {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setOut(new PrintStream(outContent));


        Mockito.when(userRepository.createUser("login")).thenReturn(true);
        Mockito.when(userRepository.createPass("login", "pass")).thenReturn(true);
        Mockito.when(userRepository.isUserPresents("login")).thenReturn(false);
        System.setIn(new ByteArrayInputStream("login\n0\nlogin\npass\npass".getBytes()));
        service.registrationUser();
        System.setOut(originalOut);
        System.out.println(outContent);
    }
}






