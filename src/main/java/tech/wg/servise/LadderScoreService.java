package tech.wg.servise;

import tech.wg.dao.ScoreEntity;
import tech.wg.tools.Grammar;

import java.util.List;

public class LadderScoreService {
    private ScoreService scoreService;
    private Grammar grammar;


    public void topTen() {
        List<ScoreEntity> topTen = scoreService.getTopPlayers(10);
        String top = String.format("%-15s|%-5s|%-9s|%-7s|%-4s%n", "Логин", "Побед", "Поражений", "Процент", "Очки");
        for (int i = 0; i < topTen.size(); i++) {
            ScoreEntity scoreEntity = topTen.get(i);
            top += String.format("%-15s|%-5s|%-9s|%-7.2f|%-4s%n", scoreEntity.getLogin(), scoreEntity.getWin(),
                    scoreEntity.getLoss(), scoreEntity.getWinRate(), scoreEntity.getScore());
        }
        grammar.write(top);
    }

}
