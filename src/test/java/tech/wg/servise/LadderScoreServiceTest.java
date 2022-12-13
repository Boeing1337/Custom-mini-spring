package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.dao.ScoreEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LadderScoreServiceTest {
    @Mock
    ScoreService scoreService;

    @InjectMocks
    LadderScoreService service;

    @Test
    void topTen() {
        List<ScoreEntity> listScoreEntity = new ArrayList<>();
        ScoreEntity player1 = new ScoreEntity("Atr", 10, 0, 100.00, 1000);
        listScoreEntity.add(player1);
        Mockito.when(scoreService.getTopPlayers(10)).thenReturn(
                listScoreEntity);
        Assertions.assertEquals("Логин          |Побед|Поражений|Процент|Очки\r\n" +
                "Atr            |10   |0        |100,00 |1000\r\n", service.topTen());

    }
}