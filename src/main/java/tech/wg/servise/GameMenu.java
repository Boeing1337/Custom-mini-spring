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
            grammar.println("Введите 1 для начала новой новой игры");
            grammar.println("Введите 2 для продолжения ранее начатой игры");
            grammar.println("Введите 3 для просмотра личной статистики");
            grammar.println("Введите 4 для просмотра списка лидеров");
            grammar.println("Введите 0 для выхода из игры");
            switch (grammar.nextLine()) {
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
                    grammar.println("Нет такой команды. Попробуйте ещё раз");
                    break;
            }
        }
    }


}
