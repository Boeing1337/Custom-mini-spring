package tech.wg.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.tools.MockGrammar;


@ExtendWith(MockitoExtension.class)
class GameMenuTest {
    @Spy
    MockGrammar mockGrammar;
    @Mock
    MyScoreService myScoreService;

    @Mock
    LadderScoreService ladderScoreService;

    @Mock
    TheGameService theGameService;

    @InjectMocks
    GameMenu service;

    @Test
    void startGameMenu() {
        mockGrammar.initWithInput("1\n0");
        service.startGameMenu();
        Mockito.verify(theGameService, Mockito.times(1)).theGameNew();
    }

    @Test
    void startGameMenu1() {
        mockGrammar.initWithInput("2\n0");
        service.startGameMenu();
        Mockito.verify(theGameService, Mockito.times(1)).theGameContinue();
    }

    @Test
    void startGameMenu2() {
        mockGrammar.initWithInput("3\n0\n0");
        service.startGameMenu();
        Mockito.verify(myScoreService, Mockito.times(1)).showMyStat();

    }

    @Test
    void startGameMenu3() {
        mockGrammar.initWithInput("4\n0\n");
        service.startGameMenu();
        Mockito.verify(ladderScoreService, Mockito.times(1)).showLadderTop();

    }

}