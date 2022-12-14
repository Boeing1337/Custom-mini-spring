package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.tools.Grammar;

@Component
public class GameMenu {
    @InjectObject
    private Grammar grammar;
    @InjectObject
    private MyScoreService myScoreService;
    @InjectObject
    private LadderScoreService ladderScoreService;
    @InjectObject
    private TheGameService theGameService;


    public void startGameMenu() {

        while (true) {
            grammar.write("Введите 1 для начала новой новой игры");
            grammar.write("Введите 2 для продолжения ранее начатой игры");
            grammar.write("Введите 3 для просмотра личной статистики");
            grammar.write("Введите 4 для просмотра списка лидеров");
            grammar.write("Введите 0 для выхода из игры");
            switch (grammar.readLine()) {
                case "1":
                    theGameService.theGameNew();
                    break;
                case "2":
                    theGameService.theGameContinue();
                    break;
                case "3":
                    myScoreService.showMyStat();
                    break;
                case "4":
                    ladderScoreService.showLadderTop();
                    break;
                case "0":
                    return;
                default:
                    grammar.write("Нет такой команды. Попробуйте ещё раз");
                    break;
            }
        }
    }


}
