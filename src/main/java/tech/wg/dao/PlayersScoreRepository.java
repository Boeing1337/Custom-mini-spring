package tech.wg.dao;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Log4j2
public class PlayersScoreRepository {
    private final static String SEPARATOR = ";";
    @InjectProperty
    private String scoreFileName;

   private String convertScoreEntityToString(ScoreEntity in) {
        return String.format("%s;%s;%s;%s;%s", in.getLogin(), in.getWin(), in.getLoss(), in.getWinRate(), in.getScore());
    }

    private ScoreEntity convertStringToScoreEntity(String in) {
        String[] result = in.split(SEPARATOR);
        return new ScoreEntity(result[0], parseInt(result[1]), parseInt(result[2]), parseDouble(result[3]), Long.parseLong(result[4]));
    }


    public Optional<ScoreEntity> saveScore(ScoreEntity scoreEntity) {
        List<ScoreEntity> allPlayerScore = findAllPlayerScore();
        int i = allPlayerScore.indexOf(scoreEntity);
        ScoreEntity result = null;
        if (i == -1) {
            allPlayerScore.add(scoreEntity);
        } else {
            result = allPlayerScore.get(i);
            result.setWin(scoreEntity.getWin());
            result.setLoss(scoreEntity.getLoss());
            result.setWinRate(scoreEntity.getWinRate());
            result.setScore(scoreEntity.getScore());
        }
        writeScores(allPlayerScore);
        return Optional.ofNullable(result);
    }

    public List<ScoreEntity> findAllPlayerScore() {
        var resultList = new ArrayList<ScoreEntity>();
        var file = new File(scoreFileName);
        try (var scanner = new Scanner(file, UTF_8)) {
            while (scanner.hasNextLine()) {
                var scoreEntity = convertStringToScoreEntity(scanner.nextLine());
                resultList.add(scoreEntity);
            }
        } catch (Exception e) {
            log.error("нет файла с очками.", e);
        }
        return resultList;
    }

    public Optional<ScoreEntity> findScoreBy(String login) {
        Optional<ScoreEntity> result = Optional.empty();
        if (login == null || login.isBlank()) {
            return result;
        }
        for (ScoreEntity scoreEntity : findAllPlayerScore()) {
            if (scoreEntity.getLogin().equals(login)) {
                result = Optional.of(scoreEntity);
                break;
            }
        }
        return result;
    }

    private boolean writeScores(List<ScoreEntity> dataToWrite) {
        StringBuilder result = new StringBuilder();
        for (ScoreEntity scoreEntity : dataToWrite) {
            result.append(convertScoreEntityToString(scoreEntity)).append("\n");
        }
        try (FileWriter file = new FileWriter(scoreFileName)) {
            file.write(result.toString());
        } catch (Exception e) {
            log.warn("Не получилось записать данные.", e);
            return false;
        }
        return true;
    }

    public void deleteScoreBy(ScoreEntity entity) {
        List<ScoreEntity> allPlayerScore = findAllPlayerScore();
        if (allPlayerScore.remove(entity)) {
            writeScores(allPlayerScore);
        } else {
            log.info("Не удалось удалить счёт игрока.");
        }
    }
}
