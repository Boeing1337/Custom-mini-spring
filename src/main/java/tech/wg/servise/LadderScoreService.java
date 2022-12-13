package tech.wg.servise;

import tech.wg.dao.ScoreEntity;
import tech.wg.tools.Grammar;

import java.util.List;

import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

public class LadderScoreService {
    private ScoreService scoreService;
    private Grammar grammar;


    public void topTen() {
        List<ScoreEntity> topTen = scoreService.getTopPlayers(10);
        String top = format(ENGLISH, "%-15s|%-5s|%-9s|%-7s|%-4s%n", "Логин", "Побед", "Поражений", "Процент", "Очки");
        for (ScoreEntity scoreEntity : topTen) {
            top += format(ENGLISH, "%-15s|%-5s|%-9s|%-7.2f|%-4s%n", scoreEntity.getLogin(), scoreEntity.getWin(),
                    scoreEntity.getLoss(), scoreEntity.getWinRate(), scoreEntity.getScore());
        }
        grammar.write(top);
    }

}
