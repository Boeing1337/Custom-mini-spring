package tech.wg.servise;

import tech.wg.dao.ScoreEntity;

import java.util.List;

public class LadderScoreService {
    private final ScoreService scoreService;

    public LadderScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

//    public static void main(String[] args) {
//        String s = String.format("%-15s|%-5s|%-9s|%-7s|%-4s%n","Логин", "Побед", "Поражений", "Процент", "Очки");
//        s += String.format("%-15s|%-5s|%-9s|%-7.2f|%-4s%n","Atr",10,0,100.00,1000);
//        System.out.println(s);
//    }

    public String topTen() {
        List<ScoreEntity> topTen = scoreService.getTopPlayers(10);
        String top = String.format("%-15s|%-5s|%-9s|%-7s|%-4s%n", "Логин", "Побед", "Поражений", "Процент", "Очки");
        for (int i = 0; i < topTen.size(); i++) {
            ScoreEntity scoreEntity = topTen.get(i);
            top += String.format("%-15s|%-5s|%-9s|%-7.2f|%-4s%n", scoreEntity.getLogin(), scoreEntity.getWin(),
                    scoreEntity.getLoss(), scoreEntity.getWinRate(), scoreEntity.getScore());
        }
        return top;
    }

}
