package tech.wg.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestionRepositoryTest {

    @Test
    void getAllAnswers() {
        QuestionRepository arr = new QuestionRepository();
        Assertions.assertEquals(33, arr.getAnswers().size());
    }

    @Test
    void getAllQuestions() {
        QuestionRepository arr = new QuestionRepository();
        Assertions.assertEquals(33, arr.getQuestions().size());
    }

    @Test
    void getQuestionByLetter() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionAnswerByLetter("А");
        Assertions.assertEquals("АБРИКОС", a.get(2));
    }

    @Test
    void addQuestionsInAConvenient() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionAnswerByLetter("Г");
        Assertions.assertEquals(a.size(), arr.getQuestionAnswerByLetter("Г").size());
        arr.addQuestionAnswers("CCczcxc", "ГГ");
        Assertions.assertEquals(a.size() + 2, arr.getQuestionAnswerByLetter("Г").size());
        Assertions.assertEquals("ГГ", arr.getQuestionAnswerByLetter("Г").get(a.size() + 1));
        arr.deleteQuestions("Г", arr.getQuestionAnswerByLetter("Г").size() / 2 - 1);
        arr.deleteAnswers("Г", arr.getQuestionAnswerByLetter("Г").size() / 2 - 1);
        Assertions.assertEquals(a.size(), arr.getQuestionAnswerByLetter("Г").size());
    }

    @Test
    void setQuestions() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionAnswerByLetter("А");
        Assertions.assertEquals("Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий", a.get(1));
        arr.setQuestions("А", 0, "Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий Я");
        Assertions.assertEquals("Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий Я", arr.getQuestionAnswerByLetter("А").get(1));
        arr.setQuestions("А", 0, "Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий");
    }

    @Test
    void setAnswers() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionAnswerByLetter("Ъ");
        arr.setAnswers("Ъ", a.size() - 3, "ответ из теста " + a.size());
        Assertions.assertEquals("ОТВЕТ ИЗ ТЕСТА " + a.size(), arr.getQuestionAnswerByLetter("Ъ").get(a.size() - 1));
    }
}