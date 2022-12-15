package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.annotations.InjectProperty;
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
    @InjectProperty
    private long pointsForWinning;
    @InjectProperty
    private long pointForLoosing;
    @InjectProperty
    private long pointsForError;
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

    public long commitWinLoose(int i) {
        if (i > 1 || i < -1 || i == 0) {
            throw new IllegalArgumentException("Значение должно быть: 1 или -1");
        }
        ScoreEntity result = getScore().orElseThrow();
        int win = result.getWin();
        int loose = result.getLoss();
        long score = result.getScore();
        if (i == 1) {
            win++;
            score += pointsForWinning;
            result.setWin(win);
            result.setLoss(loose);
            result.setWinRate(((double) win / (win + loose)) * 100.0);
            result.setScore(score);
            playersScoreRepository.saveScore(result);
            return pointsForWinning;
        }else {
            loose++;
            score += pointForLoosing;result.setWin(win);
            result.setLoss(loose);
            result.setWinRate(((double) win / (win + loose)) * 100.0);
            result.setScore(score);
            playersScoreRepository.saveScore(result);
            return pointForLoosing;
        }


    }

    public long commitAnswerMismatch() {
        ScoreEntity result = playersScoreRepository.findScoreBy(GlobalVariable.getCurrentUser().getLogin()).orElseThrow();
        result.setScore(result.getScore() + pointsForError);
        playersScoreRepository.saveScore(result);
        return pointsForError;
    }
}
