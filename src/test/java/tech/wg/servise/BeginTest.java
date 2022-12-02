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
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionRepository;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserGameStateRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static tech.wg.dao.QuestionRepository.ANY_NEW_LINE;
import static tech.wg.dao.QuestionRepository.SIMPLE_NEW_LINE;


@ExtendWith(MockitoExtension.class)
class BeginTest {

    public static final String TEST_QUESTION = "TEST QUESTION";
    @Spy
    MockGrammar mockGrammar;
    @Mock
    UserGameStateRepository userGameStateRepository;
    @Mock
    KeywordsRepository keywordsRepository;
    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    BeginingTheGame targetToTest;

    @BeforeEach
    void initialization() {
        String login = "Art";
        GlobalVariable.setCurrentUser(new UserEntity(login, "123"));
    }

    @Test
    void theGameContinue() {
        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(questionRepository.getQuestionAnswerByLetter(any())).thenReturn(
                List.of("ЕБУЧАЯ_БУКВА", TEST_QUESTION, "ЕБУЧИЙ_ОТВЕТ"));
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("RF;**");
        mockGrammar.setInputContent("1\nN\nR\n0");
        targetToTest.theGameContinue();
        Assertions.assertEquals("[*, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\n" +
                "TEST QUESTION\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Введена не верная буква, попробуте еще раз\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Верно\n" +
                "[R, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад", mockGrammar.getOut());

    }

    @Test
    void theGameContinue2() {
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("");
        mockGrammar.setInputContent("1\nN\nR\n0");
        targetToTest.theGameContinue();
        Assertions.assertEquals(
                "Нет игры, кторую можно продолжить. Начни новую игру", mockGrammar.getOut());

    }

    @Test
    void theGameNew() {
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(List.of("ДО"));
        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(questionRepository.getQuestionAnswerByLetter(any())).thenReturn(
                List.of("ЕБУЧАЯ_БУКВА", TEST_QUESTION, "ЕБУЧИЙ_ОТВЕТ"));
        mockGrammar.setInputContent("1\nа\nд\n0");
        targetToTest.theGameNew();
        Assertions.assertEquals("[*, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\n" +
                "TEST QUESTION\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Введена не верная буква, попробуте еще раз\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\n" +
                "Верно\n" +
                "[Д, *]\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад", mockGrammar.getOut());
    }
}