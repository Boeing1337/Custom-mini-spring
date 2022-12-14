package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.ScoreEntity;
import tech.wg.tools.Grammar;

import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

@Component
public class MyScoreService {
    @InjectObject
    private ScoreService scoreService;
    @InjectObject
    private Grammar grammar;


    public void showMyStat() {
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
        grammar.readLine();
    }
}
