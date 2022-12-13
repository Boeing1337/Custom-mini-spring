package tech.wg.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.UserEntity;
import tech.wg.tools.MockGrammar;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MyScoreServiceTest {
    @Spy
    MockGrammar mockGrammar;

    @Mock
    ScoreService scoreService;

    @InjectMocks
    MyScoreService service;

    @Test
    void myStat() {
        String login = "Art";
        GlobalVariable.setCurrentUser(new UserEntity(login, "123"));
        mockGrammar.initWithInput("");
        Mockito.when(scoreService.getScore()).thenReturn(Optional.empty());

        service.myStat();
        assertEquals("Логин:                    Art\n" +
                "Количество побед:         0\n" +
                "Количество поражений:     0\n" +
                "Процент побед:            0.00%\n" +
                "Заработанно очков:        0", mockGrammar.getOut());

    }
}