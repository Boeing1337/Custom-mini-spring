package tech.wg.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionEntity;
import tech.wg.dao.QuestionRepository;
import tech.wg.tools.MockGrammar;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService = new AdminService();
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private KeywordsRepository keywordsRepository;
    @Spy
    private MockGrammar grammar = new MockGrammar();

    @Test
    void testMenuWords1() {
        List<String> list = List.of("ВЕНИК", "ДОКЛАД", "ШУФЛЯДКА", "ЛЕПРЕКОН", "ФОТОАРХИВ", "АБСОЛЮТИЗМ");
        String input = "1\n1\n0\n0";
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант: \n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ВЕНИК\n" +
                        "ДОКЛАД\n" +
                        "ШУФЛЯДКА\n" +
                        "ЛЕПРЕКОН\n" +
                        "ФОТОАРХИВ\n" +
                        "АБСОЛЮТИЗМ\n" +
                        "\n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: \n" +
                        "-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант:"
                , result);
    }

    @Test
    void testMenuWords2() {
        List<String> list = List.of("ВЕНИК", "ДОКЛАД", "ШУФЛЯДКА", "ЛЕПРЕКОН", "ФОТОАРХИВ", "АБСОЛЮТИЗМ");
        String input = "1\n2\nФифА\n1\n0\n0";
        Mockito.when(keywordsRepository.addKeywords(Collections.singleton("ФИФА"))).thenReturn(list);
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант: \n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: Введите новое слово: \n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ВЕНИК\n" +
                        "ДОКЛАД\n" +
                        "ШУФЛЯДКА\n" +
                        "ЛЕПРЕКОН\n" +
                        "ФОТОАРХИВ\n" +
                        "АБСОЛЮТИЗМ\n" +
                        "\n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: \n" +
                        "-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант:"
                , result);
    }

    @Test
    void testMenuWords3() {
        List<String> list = List.of("ВЕНИК", "ДОКЛАД", "ШУФЛЯДКА", "ЛЕПРЕКОН", "ФОТОАРХИВ", "АБСОЛЮТИЗМ");
        String input = "1\n3\nФифа\nФуфлО\n1\n0\n0";
        Mockito.when(keywordsRepository.editKeywords("ФИФА", "ФУФЛО")).thenReturn(list);
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант: \n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: Введите слово которое хотите изменить: Введите новое слово\n" +
                        "\n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ВЕНИК\n" +
                        "ДОКЛАД\n" +
                        "ШУФЛЯДКА\n" +
                        "ЛЕПРЕКОН\n" +
                        "ФОТОАРХИВ\n" +
                        "АБСОЛЮТИЗМ\n" +
                        "\n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: \n" +
                        "-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант:"
                , result);
    }

    @Test
    void testMenuWords4() {
        List<String> list = List.of("ВЕНИК", "ДОКЛАД", "ШУФЛЯДКА", "ЛЕПРЕКОН", "ФОТОАРХИВ", "АБСОЛЮТИЗМ");
        String input = "1\n4\nДОКЛАД\n1\n0\n0";
        Mockito.when(keywordsRepository.readKeywords()).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант: \n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: Введите слово которое хотите удалить: \n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ВЕНИК\n" +
                        "ДОКЛАД\n" +
                        "ШУФЛЯДКА\n" +
                        "ЛЕПРЕКОН\n" +
                        "ФОТОАРХИВ\n" +
                        "АБСОЛЮТИЗМ\n" +
                        "\n" +
                        "-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: \n" +
                        "-----Категория файлов-----\n" +
                        "1) Загаданные слова.\n" +
                        "2) Вопросы и ответы.\n" +
                        "0) Выход из Админ-панели.\n" +
                        "Выберете вариант:"
                , result);
        //проверяет что отработала команда
        Mockito.verify(keywordsRepository, Mockito.times(1)).deleteKeyword("ДОКЛАД");
    }

    @Test
    void testMenuQuestsAnswers1() {
        List<QuestionEntity> list = List.of();
        String input = "2\n1\nг\n0\n0";
        Mockito.when(questionRepository.getQuestionAnswerByLetter("г")).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите букву русского алфавита: \n" +
                "\n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: \n" +
                "-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант:", result);
    }

    @Test
    void testMenuQuestsAnswers2() {
        List<QuestionEntity> list = List.of(new QuestionEntity("Фифа", "гГгГ"));
        String input = "2\n2\nФифа\nгГгГ\n1\nг\n0\n0";
        Mockito.when(questionRepository.getQuestionAnswerByLetter("г")).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите вопрос: \n" +
                "Введите ответ: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите букву русского алфавита: \n" +
                "1) Фифа\n" +
                "1) гГгГ\n" +
                "\n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: \n" +
                "-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант:", result);
        Mockito.verify(questionRepository, Mockito.times(1)).addQuestionAnswers("Фифа", "гГгГ");
    }

    @Test
    void testMenuQuestsAnswers3() {
        List<QuestionEntity> list = List.of(new QuestionEntity("Фифа", "гГгГ"));
        String input = "2\n3\nг\n1\nфуфло\n1\nг\n0\n0";
        Mockito.when(questionRepository.getQuestionAnswerByLetter("г")).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Какая первая буква ответа на вопрос который хотите изменить: \n" +
                "\n" +
                "1) Фифа\n" +
                "Выберете вариант вопроса: \n" +
                "Введите новый вопрос в одну строку: \n" +
                "\n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите букву русского алфавита: \n" +
                "1) Фифа\n" +
                "1) гГгГ\n" +
                "\n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: \n" +
                "-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант:", result);
        Mockito.verify(questionRepository, Mockito.times(1)).setQuestions("г", 0, "фуфло");
    }

    @Test
    void testMenuQuestsAnswers4() {
        List<QuestionEntity> list = List.of(new QuestionEntity("Фифа", "гГгГ"));
        String input = "2\n4\nг\n1\nггг\n1\nг\n0\n0";
        Mockito.when(questionRepository.getQuestionAnswerByLetter("г")).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Какая первая буква ответа: \n" +
                "1) гГгГ\n" +
                "Выберете вариант ответа: \n" +
                "Введите новый ответ на выбранную букву: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите букву русского алфавита: \n" +
                "1) Фифа\n" +
                "1) гГгГ\n" +
                "\n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: \n" +
                "-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант:", result);
        Mockito.verify(questionRepository, Mockito.times(1)).setAnswers("г", 0, "ггг");
    }

    @Test
    void testMenuQuestsAnswers5() {
        List<QuestionEntity> list = List.of();
        String input = "2\n5\nг\n1\n1\nг\n0\n0";
        Mockito.when(questionRepository.getQuestionAnswerByLetter("г")).thenReturn(list);
        grammar.initWithInput(input);
        adminService.action();
        String result = grammar.getOut();
        Assertions.assertEquals("-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите букву русского алфавита: \n" +
                "Выберете вариант: \n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: Введите букву русского алфавита: \n" +
                "\n" +
                "-----Работа с вопросами и ответами-----\n" +
                "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                "2) Добавить вопрос и ответ.\n" +
                "3) Изменить вопрос.\n" +
                "4) Изменить ответ.\n" +
                "5) Удалить вопрос и ответ.\n" +
                "0) назад.\n" +
                "Выберете вариант: \n" +
                "-----Категория файлов-----\n" +
                "1) Загаданные слова.\n" +
                "2) Вопросы и ответы.\n" +
                "0) Выход из Админ-панели.\n" +
                "Выберете вариант:", result);
        Mockito.verify(questionRepository, Mockito.times(1)).deleteQuestions("г", 0);
    }
}