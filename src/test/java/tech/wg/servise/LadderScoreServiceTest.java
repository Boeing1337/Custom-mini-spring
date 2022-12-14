package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.dao.ScoreEntity;
import tech.wg.tools.MockGrammar;

import java.util.ArrayList;
import java.util.List;
@ExtendWith(MockitoExtension.class)
class LadderScoreServiceTest {
    @Spy
    MockGrammar mockGrammar;
    @Mock
    ScoreService scoreService;
    @InjectMocks
    LadderScoreService service;

    @Test
    void topTen() {
        List<ScoreEntity> listScoreEntity = new ArrayList<>();
        ScoreEntity player1 = new ScoreEntity("Atr", 10, 0, 100.00, 1000);
        listScoreEntity.add(player1);
        mockGrammar.initWithInput("0");
        Mockito.when(scoreService.getTopPlayers(10)).thenReturn(listScoreEntity);
        service.showLadderTop();
        Assertions.assertEquals("Логин          |Побед|Поражений|Процент|Очки\n" +
                "Atr            |10   |0        |100.00 |1000\n" +
                "\n" +
                "Введите 0, чтоб вернуться назад", mockGrammar.getOut());

    }
}