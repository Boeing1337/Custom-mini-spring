package tech.wg.servise;

import tech.wg.context.GlobalVariable;
import tech.wg.dao.ScoreEntity;
import tech.wg.tools.Grammar;

public class MyScoreService {

    private final ScoreService scoreService;
    private final Grammar grammar;

    public MyScoreService(ScoreService scoreService, Grammar grammar) {
        this.scoreService = scoreService;
        this.grammar = grammar;
    }

    public void myStat() {
        ScoreEntity scoreEntity = scoreService.getScore().orElse(buildScoreEntity());
        grammar.write(String.format("%-25s %s%n%-25s %d%n%-25s %d%n%-25s %.2f%%%n%-25s %s%n", "Логин:",
                scoreEntity.getLogin(), "Количество побед:", scoreEntity.getWin(), "Количество поражений:",
                scoreEntity.getLoss(), "Процент побед:", scoreEntity.getWinRate(), "Заработанно очков:",
                scoreEntity.getScore()));
    }

    private ScoreEntity buildScoreEntity() {
        return new ScoreEntity(GlobalVariable.currentUser.getLogin(), 0, 0, 0.00, 0);
    }
}