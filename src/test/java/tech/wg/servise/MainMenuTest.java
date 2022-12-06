package tech.wg.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.tools.MockGrammar;

@ExtendWith(MockitoExtension.class)
class MainMenuTest {
    @Spy
    MockGrammar mockGrammar;
    @Mock
    LoginService loginService;

    @Mock
    RegistrationUsers registrationUsers;

    @InjectMocks
    MainMenu service;

    @Test
    void startMainMenu() {
        mockGrammar.setInputContent("1\n0");
        service.startMainMenu();
        Mockito.verify(loginService, Mockito.times(1)).authorization();
    }

    @Test
    void startMainMenu1() {
        mockGrammar.setInputContent("2\n0");
        service.startMainMenu();
        Mockito.verify(registrationUsers, Mockito.times(1)).registrationUser();
    }
    @Test
    void startMainMenu2() {
        mockGrammar.setInputContent("3\n2\n0");
        service.startMainMenu();
        Mockito.verify(registrationUsers, Mockito.times(1)).registrationUser();
    }
}