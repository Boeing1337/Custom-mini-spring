package tech.wg.servise;

import tech.wg.context.GlobalVariable;
import tech.wg.dao.ScoreEntity;
import tech.wg.tools.Grammar;

import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

public class MyScoreService {

    private final ScoreService scoreService;
    private final Grammar grammar;

    public MyScoreService(ScoreService scoreService, Grammar grammar) {
        this.scoreService = scoreService;
        this.grammar = grammar;
    }

    public void myStat() {
        ScoreEntity scoreEntity = scoreService.getScore().orElse(buildScoreEntity());
        grammar.write(format(ENGLISH, "%-25s %s%n%-25s %d%n%-25s %d%n%-25s %.2f%%%n%-25s %s%n", "Логин:",
                scoreEntity.getLogin(), "Количество побед:", scoreEntity.getWin(), "Количество поражений:",
                scoreEntity.getLoss(), "Процент побед:", scoreEntity.getWinRate(), "Заработанно очков:",
                scoreEntity.getScore()));
        refund();
    }

    private ScoreEntity buildScoreEntity() {
        return new ScoreEntity(GlobalVariable.currentUser.getLogin(), 0, 0, 0.00, 0);
    }

    private void refund() {
        grammar.write("Введите 0, чтоб вернуться назад");
        while (grammar.readInt() != 0) {
            grammar.write("Введена не верная команда");
        }
    }
}
