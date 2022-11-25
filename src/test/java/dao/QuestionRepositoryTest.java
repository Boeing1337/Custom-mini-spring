package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestionRepositoryTest {

    @Test
    void reading() {
        QuestionRepository arr = new QuestionRepository();
        Assertions.assertEquals(33, arr.getBukvi().length);
    }

    @Test
    void readAnswers() {
        QuestionRepository arr = new QuestionRepository();
        Assertions.assertEquals(33, arr.getAllAnswers().size());
    }

    @Test
    void readQuestions() {
        QuestionRepository arr = new QuestionRepository();
        Assertions.assertEquals(33, arr.getAllQuestions().size());
    }

    @Test
    void getQuestionBy() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionBy("А");
        Assertions.assertEquals("АБРИКОС", a.get(2));
    }

    @Test
    void submitQuestionsInAConvenient() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionBy("Г");
        Assertions.assertEquals(a.size(), arr.getQuestionBy("Г").size());
        arr.addQuestionAnswers("CCczcxc", "ГГ");
        Assertions.assertEquals(a.size() + 2, arr.getQuestionBy("Г").size());
        Assertions.assertEquals("ГГ", arr.getQuestionBy("Г").get(a.size() + 1));
    }
}