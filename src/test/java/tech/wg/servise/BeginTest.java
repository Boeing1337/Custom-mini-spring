package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionRepository;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserGameStateRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static tech.wg.dao.QuestionRepository.ANY_NEW_LINE;
import static tech.wg.dao.QuestionRepository.SIMPLE_NEW_LINE;


@ExtendWith(MockitoExtension.class)
class BeginTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @InjectMocks
    TheGameService targetToTest;
    @Mock
    UserGameStateRepository userGameStateRepository;
    @Mock
    KeywordsRepository keywordsRepository;
    @Mock
    QuestionRepository questionRepository;

    @BeforeEach
    void initialization() {
        System.setOut(new PrintStream(outContent));
        String login = "Art";
        GlobalVariable.setCurrentUser(new UserEntity(login, "123"));
        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(questionRepository.getQuestionAnswerByLetter(any())).thenReturn(List.of("Короткая черточка, употребляется как знак переноса",
                "Короткая черточка, употребляется как знак переноса", "Короткая черточка, употребляется как знак переноса"));
    }

    @Test
    void theGameContinue() {
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("ДОКЛАД;****А*");
        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n0\n".getBytes()));
        targetToTest.theGameContinue();
        System.setOut(originalOut);
        Assertions.assertEquals(("[*, *, *, *, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, *, *, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Введена не верная буква, попробуте еще раз\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, К, *, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n").replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE).trim(), outContent.toString().trim());

    }


    @Test
    void theGameNew() {
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(List.of("ДОКЛАД"));
        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n0\n".getBytes()));
        targetToTest.theGameNew();
        System.setOut(originalOut);
        Assertions.assertEquals(("[*, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Введена не верная буква, попробуте еще раз\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, К, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n").replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE).trim(), outContent.toString().trim());
    }

    @Test
    void theGameNew2() {
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(List.of("ДОКЛАД"));
        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n2\nо\n4\nл\n5\nа\n6\nд\n".getBytes()));
        targetToTest.theGameNew();
        System.setOut(originalOut);
        Assertions.assertEquals(("[*, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Введена не верная буква, попробуте еще раз\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, К, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, Л, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, Л, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, Л, А, Д]\r\n" +
                "Поздравляю, вы набрали 100 очков\r\n").replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE).trim(), outContent.toString().trim());
    }
}