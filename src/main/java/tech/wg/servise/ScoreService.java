package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.PlayersScoreRepository;
import tech.wg.dao.ScoreEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparingLong;

@Component
public class ScoreService {
    private final int winCount = 1;
    private final int looseCount = 1;
    @InjectObject
    private PlayersScoreRepository playersScoreRepository;


    public List<ScoreEntity> getTopPlayers(int count) {
        List<ScoreEntity> scoreEntities = playersScoreRepository.findAllPlayerScore();
        List<ScoreEntity> result = new ArrayList<>();
        sortingByScore(scoreEntities);
        for (int i = 0; scoreEntities.size() > i && i < count; i++) {
            result.add(scoreEntities.get(i));
        }
        return result;
    }

    private void sortingByScore(List<ScoreEntity> cache) {
        cache.sort(comparingLong(ScoreEntity::getScore));
        Collections.reverse(cache);
    }

    public Optional<ScoreEntity> getScore() {
        return playersScoreRepository.findScoreBy(GlobalVariable.getCurrentUser().getLogin());
    }

    public void commitWinLoose(int i) {
        if (i > 1 || i < -1 || i == 0) {
            throw new IllegalArgumentException("Значение должно быть: 1 или -1");
        }
        ScoreEntity result = getScore().orElseThrow();
        int win = result.getWin();
        int loose = result.getLoss();
        long score = result.getScore();
        if (i == 1) {
            win++;
            score += 100;
        }else {
            loose++;
            score -=100;
        }
        result.setWin(win);
        result.setLoss(loose);
        result.setWinRate(((double) win / (win + loose)) * 100.0);
        result.setScore(score);
        playersScoreRepository.saveScore(result);
    }

    public void commitAnswerMismatch() {
        ScoreEntity result = playersScoreRepository.findScoreBy(GlobalVariable.getCurrentUser().getLogin()).orElseThrow();
        result.setScore(result.getScore() - 50);
        playersScoreRepository.saveScore(result);
    }
}
