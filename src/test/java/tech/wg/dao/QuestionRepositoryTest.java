package tech.wg.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.extention.ExtendWIthTech;
import tech.ioc.annotations.InjectProperty;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ExtendWIthTech
class QuestionRepositoryTest {

    private static final String B_QUESTION = "Б_ВОПРОС";
    private static final String B_ANSWER = "Б_ОТВЕТ";
    private final QuestionRepository repository = new QuestionRepository();
    private static String letter;

    @InjectProperty
    private String questionsFileName;

    @BeforeEach
    void createTestFile() {
        try (FileWriter writer = new FileWriter(questionsFileName)) {
            writer.write("А:\n" +
                    ";\n\n" +
                    "Б:\n" +
                    "/" + B_QUESTION + "/\n" +
                    B_ANSWER + "\n" +
                    ";\n\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void deleteTestFile() {
        try {
            Files.deleteIfExists(Path.of(questionsFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getQuestionByLetter() {
        List<QuestionEntity> a = repository.getQuestionAnswerByLetter("Б");
        Assertions.assertEquals(new QuestionEntity(B_QUESTION, B_ANSWER), a.get(0));
    }

    @Test
    void addQuestionsInAConvenient() {
        QuestionEntity addedEntity = new QuestionEntity(B_QUESTION, B_ANSWER);
        letter = "Б";
        List<QuestionEntity> result = repository.getQuestionAnswerByLetter(letter);
        repository.addQuestionAnswers(B_QUESTION, B_ANSWER);
        Assertions.assertEquals(result.size() + 1, repository.getQuestionAnswerByLetter(letter).size());
        Assertions.assertEquals(addedEntity, repository.getQuestionAnswerByLetter(letter).get(result.size()));

        repository.deleteQuestions(letter, repository.getQuestionAnswerByLetter(letter).size() - 1);
        Assertions.assertEquals(result.size(), repository.getQuestionAnswerByLetter(letter).size());
    }

    @Test
    void setQuestions() {
        List<QuestionEntity> a = repository.getQuestionAnswerByLetter("Б");
        Assertions.assertEquals(B_QUESTION, a.get(0).getQuestion());
        repository.setQuestions("Б", 0, "обновлённый вопрос");
        Assertions.assertEquals("обновлённый вопрос", repository.getQuestionAnswerByLetter("Б").get(0).getQuestion());
    }

    @Test
    void testSetAnswers() {
        List<QuestionEntity> result = repository.getQuestionAnswerByLetter("Б");
        repository.setAnswers("Б", 0, "ответ из теста " + result.size());
        Assertions.assertEquals(
                "ОТВЕТ ИЗ ТЕСТА " + result.size(),
                repository.getQuestionAnswerByLetter("Б").get(result.size() - 1).getAnswer()
        );
    }
}