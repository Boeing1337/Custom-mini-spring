package Game;

import context.GlobalVariable;
import dao.KeywordsRepository;
import dao.QuestionRepository;
import dao.UserEntity;
import dao.UserGameStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class BeginTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @InjectMocks
    begin game;

    @Mock
    UserGameStateRepository userGameStateRepository;
    @Mock
    KeywordsRepository words;
    @Mock
    QuestionRepository base;

    @BeforeEach
    void soglasovanie() {
        System.setOut(new PrintStream(outContent));
        String login = "Art";
        GlobalVariable.setCurrentUser(new UserEntity(login, "123"));
        Mockito.when(userGameStateRepository.writeProgress(any(), any(), any())).thenReturn(true);
        Mockito.when(base.getQuestionByLetter(any())).thenReturn(List.of("Короткая черточка, употребляется как знак переноса",
                "Короткая черточка, употребляется как знак переноса", "Короткая черточка, употребляется как знак переноса"));
    }

    @Test
    void theGameContinue() {
        Mockito.when(userGameStateRepository.getProgress(any())).thenReturn("ДОКЛАД;****А*");
        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n0\n".getBytes()));
        game.theGameContinue();
        System.setOut(originalOut);
        Assertions.assertEquals("[*, *, *, *, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, *, *, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Введена не верная буква, попробуте еще раз\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, К, *, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n", outContent.toString());

    }


    @Test
    void theGameNew() {
        Mockito.when(words.readKeywords()).thenReturn(List.of("ДОКЛАД"));
        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n0\n".getBytes()));
        game.theGameNew();
        System.setOut(originalOut);
        Assertions.assertEquals("[*, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Введена не верная буква, попробуте еще раз\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, К, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n", outContent.toString());
    }

    @Test
    void theGameNew2() {
        Mockito.when(words.readKeywords()).thenReturn(List.of("ДОКЛАД"));
        System.setIn(new ByteArrayInputStream("1\nд\n3\nа\nк\n2\nо\n4\nл\n5\nа\n6\nд\n".getBytes()));
        game.theGameNew();
        System.setOut(originalOut);
        Assertions.assertEquals("[*, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, *, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Введена не верная буква, попробуте еще раз\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, *, К, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, *, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, Л, *, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, Л, А, *]\r\n" +
                "Выбери номер буквы или нажми 0, чтобы вернуться назад\r\n" +
                "Короткая черточка, употребляется как знак переноса\r\n" +
                "Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад\r\n" +
                "Верно\r\n" +
                "[Д, О, К, Л, А, Д]\r\n" +
                "Поздравляю, вы набрали 100 очков\r\n", outContent.toString());
    }
}