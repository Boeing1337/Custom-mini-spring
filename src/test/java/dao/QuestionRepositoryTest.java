package dao;

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
        List<String> a = arr.getQuestionByLetter("А");
        Assertions.assertEquals("АБРИКОС", a.get(2));
    }

    @Test
    void addQuestionsInAConvenient() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionByLetter("Г");
        Assertions.assertEquals(a.size(), arr.getQuestionByLetter("Г").size());
        arr.addQuestionAnswers("CCczcxc", "ГГ");
        Assertions.assertEquals(a.size() + 2, arr.getQuestionByLetter("Г").size());
        Assertions.assertEquals("ГГ", arr.getQuestionByLetter("Г").get(a.size() + 1));
    }

    @Test
    void setQuestions() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionByLetter("Г");
        Assertions.assertEquals("Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий", a.get(1));
    }

    @Test
    void setAnswers() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionByLetter("А");
        List<List<String>> b = arr.getAnswers();
        Assertions.assertEquals("АБРИКОС", a.get(2));
        arr.setAnswers("А", 2, "АБРИКОСФ");
        Assertions.assertEquals("АБРИКОСф", arr.getQuestionByLetter("А").get(2));
    }
}