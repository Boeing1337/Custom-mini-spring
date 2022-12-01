package tech.wg.Servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.Registration.RegistrationUsers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class MainMenuTest {

    @Mock
    LoginSevise loginSevise;

    @Mock
    RegistrationUsers registrationUsers;

    @InjectMocks
    MainMenu servise;

    @Test
    void startMainMenu() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        servise.startMainMenu();
        Mockito.verify(loginSevise, Mockito.times(1)).authorization();
        System.setOut(originalOut);
    }

    @Test
    void startMainMenu1() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("2".getBytes()));
        servise.startMainMenu();
        Mockito.verify(registrationUsers, Mockito.times(1)).registrationUser();
        System.setOut(originalOut);
    }
    @Test
    void startMainMenu2() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("3\n2\n0".getBytes()));
        servise.startMainMenu();
        Mockito.verify(registrationUsers, Mockito.times(1)).registrationUser();
        System.setOut(originalOut);
    }
}