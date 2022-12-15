package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.*;
import tech.wg.tools.MockGrammar;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class TheGameServiceTest {

    public static final String TEST_QUESTION = "TEST QUESTION";
    @Spy
    MockGrammar mockGrammar;
    @Mock
    UserGameStateRepository userGameStateRepository;
    @Mock
    KeywordsRepository keywordsRepository;
    @Mock
    QuestionRepository questionRepository;
    @Mock
    ScoreService scoreService;
    @InjectMocks
    TheGameService targetToTest;

    @BeforeEach
    void initialization() {
        String login = "Art";
        GlobalVariable.setCurrentUser(new UserEntity(login, "123"));
    }

    @Test
    void theGameContinue() {
        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(questionRepository.getQuestionAnswerByLetter(any())).thenReturn(
                List.of(new QuestionEntity(TEST_QUESTION, "ЕБУЧИЙ_ОТВЕТ")));
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("RF;**");
        Mockito.when(scoreService.commitAnswerMismatch()).thenReturn(50l);
        mockGrammar.initWithInput("1\nN\nR\n0\n");
        targetToTest.theGameContinue();
        Assertions.assertEquals("[*, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\n" +
                "TEST QUESTION\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Введена не верная буква.\n" +
                "Ваш счет изменился: 50 очков.\n" +
                "Попробуте еще раз\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Верно\n" +
                "[R, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад", mockGrammar.getOut());

    }

    @Test
    void theGameContinue2() {
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("");
        mockGrammar.initWithInput("1\nN\nR\n0");
        targetToTest.theGameContinue();
        Assertions.assertEquals(
                "Нет игры, которую можно продолжить. Начните новую игру", mockGrammar.getOut());

    }

    @Test
    void theGameNew() {
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("");
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(List.of("ДО"));
        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(questionRepository.getQuestionAnswerByLetter(any())).thenReturn(
                List.of(new QuestionEntity(TEST_QUESTION, "ЕБУЧИЙ_ОТВЕТ")));
        Mockito.when(scoreService.commitAnswerMismatch()).thenReturn(50l);
        mockGrammar.initWithInput("1\nа\nд\n0\n");
        targetToTest.theGameNew();
        Assertions.assertEquals("[*, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\n" +
                "TEST QUESTION\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Введена не верная буква.\n" +
                "Ваш счет изменился: 50 очков.\n" +
                "Попробуте еще раз\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Верно\n" +
                "[Д, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад", mockGrammar.getOut());
    }
}