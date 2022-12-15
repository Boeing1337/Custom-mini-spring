package tech.wg.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import tech.extention.ExtendWIthTech;
import tech.ioc.annotations.InjectProperty;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWIthTech
class PlayersScoreRepositoryTest {
    private final PlayersScoreRepository playersScoreRepository = new PlayersScoreRepository();

    @InjectProperty
    private String scoreFileName;

    @BeforeEach
    void createTestFile() {
        try (FileWriter writer = new FileWriter(scoreFileName)) {
            writer.write("pizduk;5;0;100.0;100\npizdabol;0;5;0.0;100");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void deleteTestFile() {
        try {
            Files.deleteIfExists(Path.of(scoreFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveScoreTest() {
        ScoreEntity expected = new ScoreEntity("pizduk", 5, 0, 100.0, 100);
        playersScoreRepository.saveScore(expected);
        expected.setWin(0);
        expected.setLoss(5);
        expected.setWinRate(0.0);

        ScoreEntity result = playersScoreRepository.findScoreBy("pizduk").get();
        result.setWin(0);
        result.setLoss(5);
        result.setWinRate(0.0);
        playersScoreRepository.saveScore(result);
        result = playersScoreRepository.findScoreBy("pizduk").get();

        Assertions.assertTrue(new ReflectionEquals(expected).matches(result));
    }

    @Test
    void findAllPlayerScoreTest() {
        ScoreEntity p = new ScoreEntity("pizduk", 5, 0, 100.0, 100);
        ScoreEntity q = new ScoreEntity("pizdabol", 0, 5, 0.0,100);
        List<ScoreEntity> a = new ArrayList<>();
        a.add(p);
        a.add(q);

        Assertions.assertIterableEquals(a, playersScoreRepository.findAllPlayerScore());
    }

    @Test
    void findScoreByTest() {
        ScoreEntity expected = new ScoreEntity("pizduk", 5, 0, 100.0, 100);
        ScoreEntity result = playersScoreRepository.findScoreBy("pizduk").get();
        Assertions.assertEquals(expected.getLogin(), result.getLogin());
        Assertions.assertEquals(expected.getWin(), result.getWin());
        Assertions.assertEquals(expected.getLoss(), result.getLoss());
        Assertions.assertEquals(expected.getWinRate(), result.getWinRate());
        Assertions.assertEquals(expected.getScore(), result.getScore());
    }

    @Test
    void deleteScoreByTest() {
        ScoreEntity entity = new ScoreEntity("shnuk", 5, 0, 100.0, 100);
        playersScoreRepository.saveScore(entity);
        Optional<ScoreEntity> expected = playersScoreRepository.findScoreBy("shnuk");
        Assertions.assertFalse(expected.isEmpty(), "Не сохранилось");
        playersScoreRepository.deleteScoreBy(entity);
        Optional<ScoreEntity> result = playersScoreRepository.findScoreBy("shnuk");
        Assertions.assertFalse(result.isPresent(), "Не удалилось");
    }
}