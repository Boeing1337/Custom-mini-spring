package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.annotations.InjectProperty;
import tech.wg.dao.ScoreEntity;
import tech.wg.tools.Grammar;

import java.util.List;

import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

@Component
public class LadderScoreService {
    @InjectObject
    private ScoreService scoreService;
    @InjectObject
    private Grammar grammar;
    @InjectProperty
    private int ladderCount;
    private String top;


    public void showLadderTop() {
        List<ScoreEntity> topTen = scoreService.getTopPlayers(ladderCount);
        top = format(ENGLISH, "%-15s|%-5s|%-9s|%-7s|%-4s%n", "Логин", "Побед", "Поражений", "Процент", "Очки");
        for (ScoreEntity scoreEntity : topTen) {
            top += format(ENGLISH, "%-15s|%-5s|%-9s|%-7.2f|%-4s%n", scoreEntity.getLogin(), scoreEntity.getWin(),
                    scoreEntity.getLoss(), scoreEntity.getWinRate(), scoreEntity.getScore());
        }
        grammar.write(top);
        refund();
    }

    private void refund() {
        grammar.write("Введите 0, чтоб вернуться назад");
        while (grammar.readInt() != 0) {
            grammar.write("Введена не верная команда");
        }
        grammar.readLine();
    }

}
