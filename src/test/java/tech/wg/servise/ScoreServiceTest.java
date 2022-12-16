package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import tech.extention.ExtendWIthTechAndMockito;
import tech.ioc.annotations.InjectProperty;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.PlayersScoreRepository;
import tech.wg.dao.ScoreEntity;
import tech.wg.dao.UserEntity;
import tech.wg.tools.MockGrammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWIthTechAndMockito
class ScoreServiceTest {
    @InjectMocks
    ScoreService service;
    @Mock
    PlayersScoreRepository playersScoreRepository;
    @Spy
    MockGrammar grammar;
    @InjectProperty
    private long pointsForWinning;
    @InjectProperty
    private long pointForLoosing;
    @InjectProperty
    private long pointsForError;

    @Test
    void showTopPlayers() {
        ScoreEntity expected = new ScoreEntity("login", 10, 0, 100.0, 1000);
        ScoreEntity expected1 = new ScoreEntity("login1", 10, 0, 100.0, 3000);
        ScoreEntity expected2 = new ScoreEntity("login2", 10, 0, 100.0, 2000);
        List<ScoreEntity> exp = new ArrayList<>();
        exp.add(expected);
        exp.add(expected1);
        exp.add(expected2);

        Mockito.when(playersScoreRepository.findAllPlayerScore()).thenReturn(exp);

        List<ScoreEntity> result = service.getTopPlayers(3);

        Assertions.assertEquals(exp.get(0).getScore(), result.get(0).getScore());
        Assertions.assertEquals(exp.get(1).getScore(), result.get(1).getScore());
        Assertions.assertEquals(exp.get(2).getScore(), result.get(2).getScore());
    }

    @Test
    void TestGetScore() {
        GlobalVariable.setCurrentUser(new UserEntity("login", "pass"));
        ScoreEntity expected = new ScoreEntity("login", 10, 0, 100.0, 1000);
        Mockito.when(playersScoreRepository.findScoreBy("login")).thenReturn(Optional.of(expected));
        ScoreEntity result = service.getScore().orElseThrow();
        Assertions.assertEquals(expected.getLogin(), result.getLogin());
        Assertions.assertEquals(expected.getWin(), result.getWin());
        Assertions.assertEquals(expected.getLoss(), result.getLoss());
        Assertions.assertEquals(expected.getWinRate(), result.getWinRate());
        Assertions.assertEquals(expected.getScore(), result.getScore());
    }

    @Test
    void TestCommitWinLooseLoose() {
        GlobalVariable.setCurrentUser(new UserEntity("login", "pass"));
        ScoreEntity expected = new ScoreEntity("login", 10, 1, 90.9090909090909, 900);
        Mockito.when(playersScoreRepository.findScoreBy("login"))
                .thenReturn(Optional.of(new ScoreEntity("login", 10, 0, 100.0, 1000)));
        service.commitWinLoose(-1);
        ScoreEntity result = service.getScore().orElseThrow();
        Assertions.assertEquals(expected.getLogin(), result.getLogin());
        Assertions.assertEquals(expected.getWin(), result.getWin());
        Assertions.assertEquals(expected.getLoss(), result.getLoss());
        Assertions.assertEquals(expected.getWinRate(), result.getWinRate());
        Assertions.assertEquals(expected.getScore(), result.getScore());
    }

    @Test
    void TestCommitWinLooseWin() {
        GlobalVariable.setCurrentUser(new UserEntity("login", "pass"));
        ScoreEntity expected = new ScoreEntity("login", 11, 0, 100.0, 1100);
        Mockito.when(playersScoreRepository.findScoreBy("login"))
                .thenReturn(Optional.of(new ScoreEntity("login", 10, 0, 100.0, 1000)));
        service.commitWinLoose(1);
        ScoreEntity result = service.getScore().orElseThrow();
        Assertions.assertEquals(expected.getLogin(), result.getLogin());
        Assertions.assertEquals(expected.getWin(), result.getWin());
        Assertions.assertEquals(expected.getLoss(), result.getLoss());
        Assertions.assertEquals(expected.getWinRate(), result.getWinRate());
        Assertions.assertEquals(expected.getScore(), result.getScore());
    }

    @Test
    void commitAnswerMismatch() {
        GlobalVariable.setCurrentUser(new UserEntity("login", "pass"));
        ScoreEntity expected = new ScoreEntity("login", 10, 0, 100.0, 950);
        Mockito.when(playersScoreRepository.findScoreBy("login"))
                .thenReturn(Optional.of(new ScoreEntity("login", 10, 0, 100.0, 1000)));
        service.commitAnswerMismatch();
        ScoreEntity result = service.getScore().orElseThrow();
        Assertions.assertEquals(expected.getScore(), result.getScore(), "Не получилос отнять очки у счёта");
    }
}