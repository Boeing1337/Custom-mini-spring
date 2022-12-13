package tech.wg.servise;

import tech.wg.tools.Grammar;

public class GameMenu {
    private final Grammar grammar;
    private final MyScoreService myScoreService;
    private final LadderScoreService ladderScoreService;
    private final TheGameService theGameService;


    public GameMenu(Grammar grammar, ScoreService scoreService, MyScoreService myScoreService,
                    LadderScoreService ladderScoreService, TheGameService theGameService) {
        this.grammar = grammar;
        this.myScoreService = myScoreService;
        this.ladderScoreService = ladderScoreService;
        this.theGameService = theGameService;
    }

    public void startGameMenu() {
        boolean inTheProcess = true;
        String input;
        while (inTheProcess) {
            grammar.write("Введите 1 для начала новой новой игры");
            grammar.write("Введите 2 для продолжения ранее начатой игры");
            grammar.write("Введите 3 для просмотра личной статистики");
            grammar.write("Введите 4 для просмотра списка лидеров");
            grammar.write("Введите 0 для возврата в предудущее меню");
            input = grammar.readLine();
            switch (input) {
                case "1":
                    theGameService.theGameNew();
                    break;
                case "2":
                    theGameService.theGameContinue();
                    break;
                case "3":
                    myScoreService.myStat();
                    refund();
                    break;
                case "4":
                    ladderScoreService.topTen();
                    refund();
                    break;
                case "0":
                    inTheProcess = false;
                    break;
                default:
                    grammar.write("Нет такой команды. Попробуйте ещё раз");
                    break;
            }
        }
    }

    private void refund() {
        grammar.write("Введите 0, чтоб вернуться назад");
        while (grammar.readInt() != 0) {
            grammar.write("Введена не верная команда");
        }
    }
}
