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
        arr.deleteQuestions("Г", arr.getQuestionByLetter("Г").size() / 2 - 1);
        arr.deleteAnswers("Г", arr.getQuestionByLetter("Г").size() / 2 - 1);
        Assertions.assertEquals(a.size(), arr.getQuestionByLetter("Г").size());
    }

    @Test
    void setQuestions() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionByLetter("А");
        Assertions.assertEquals("Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий", a.get(1));
        arr.setQuestions("А", 0, "Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий Я");
        Assertions.assertEquals("Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий Я", arr.getQuestionByLetter("А").get(1));
        arr.setQuestions("А", 0, "Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий");
    }

    @Test
    void setAnswers() {
        QuestionRepository arr = new QuestionRepository();
        List<String> a = arr.getQuestionByLetter("А");
        List<List<String>> b = arr.getAnswers();
        Assertions.assertEquals("АБРИКОС", a.get(2));
        arr.setAnswers("А", 0, "АБРИКОСФ");
        Assertions.assertEquals("АБРИКОСФ", arr.getQuestionByLetter("А").get(2));
        arr.setAnswers("А", 0, "АБРИКОС");
    }
}