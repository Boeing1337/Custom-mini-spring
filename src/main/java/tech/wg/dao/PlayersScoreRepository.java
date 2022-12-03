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
    private final List<ScoreEntity> cache = new ArrayList<>();


     String convertScoreEntityToString(ScoreEntity in) {
        return String.format("%s;%s;%s;%s", in.getLogin(), in.getWin(), in.getLoss(), in.getWinRate());
    }

     ScoreEntity convertStringToScoreEntity(String in) {
        String[] result = in.split(SEPARATOR);
        return new ScoreEntity(result[0], parseInt(result[1]), parseInt(result[2]), parseDouble(result[3]));
    }


    public void saveScore(ScoreEntity scoreEntity) {
        findAllPlayerScore();
        int i = cache.indexOf(scoreEntity);
        if (i == -1) {
            cache.add(scoreEntity);
        } else {
            ScoreEntity result = cache.get(i);
            result.setWin(scoreEntity.getWin());
            result.setLoss(scoreEntity.getLoss());
            result.setWinRate(scoreEntity.getWinRate());
        }
        writeCash();
        cache.clear();
        findAllPlayerScore();
    }

    public List<ScoreEntity> findAllPlayerScore() {
        if (cache.isEmpty()) {
            File file = new File(scoreFileName);
            try (Scanner scanner = new Scanner(file, UTF_8)) {
                while (scanner.hasNextLine()) {
                    ScoreEntity scoreEntity = convertStringToScoreEntity(scanner.nextLine());
                    cache.add(scoreEntity);
                }
            } catch (Exception e) {
                cache.clear();
                log.error("нет файла со словами", e);
            }
        }
        return List.copyOf(cache);
    }

    public Optional<ScoreEntity> findScoreBy(String login) {
        Optional<ScoreEntity> result = Optional.empty();
        if (login == null || login.isBlank()) {
            return result;
        }
        findAllPlayerScore();
        for (ScoreEntity scoreEntity : cache) {
            if (scoreEntity.getLogin().equals(login)) {
                result = Optional.of(scoreEntity);
            }
        }
        return result;
    }

    private boolean writeCash() {
        StringBuilder result = new StringBuilder();
        for (ScoreEntity scoreEntity : cache) {
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
        findAllPlayerScore();
        boolean remove = cache.remove(entity);
        if (remove) {
            if (!writeCash()) {
                log.info("Не удалось удалить счёт игрока.");
            }
        } else {
            log.info("Не удалось удалить счёт игрока.");
        }
        cache.clear();
        findAllPlayerScore();
    }
}
