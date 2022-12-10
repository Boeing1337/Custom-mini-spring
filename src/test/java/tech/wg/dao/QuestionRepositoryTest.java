package tech.wg.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestionRepositoryTest {

    private final QuestionRepository repository = new QuestionRepository();

    @Test
    void getAllAnswers() {
        Assertions.assertEquals(33, repository.getAnswers().size());
    }

    @Test
    void getAllQuestions() {
        Assertions.assertEquals(33, repository.getQuestions().size());
    }

    @Test
    void getQuestionByLetter() {
        List<QuestionEntity> a = repository.getQuestionAnswerByLetter("А");
        Assertions.assertEquals("АБРИКОС", a.get(0).getAnswer());
    }

    @Test
    void addQuestionsInAConvenient() {
        String question = "question";
        String answer = "Ъ";
        QuestionEntity addedEntity = new QuestionEntity(question, answer);
        String letter = "Ъ";
        List<QuestionEntity> result = repository.getQuestionAnswerByLetter(letter);
        repository.addQuestionAnswers(question, answer);
        Assertions.assertEquals(result.size() + 1, repository.getQuestionAnswerByLetter(letter).size());
        Assertions.assertEquals(addedEntity, repository.getQuestionAnswerByLetter(letter).get(result.size()));

        repository.deleteQuestions(letter, repository.getQuestionAnswerByLetter(letter).size() - 1);
        Assertions.assertEquals(result.size(), repository.getQuestionAnswerByLetter(letter).size());
    }

    @Test
    void setQuestions() {
        List<QuestionEntity> a = repository.getQuestionAnswerByLetter("А");
        Assertions.assertEquals(
                "Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                        "Прилетел к нему рой ос — Сладок мягкий", a.get(0).getQuestion()
        );
        repository.setQuestions("А", 0, "обновлённый вопрос");
        Assertions.assertEquals("обновлённый вопрос", repository.getQuestionAnswerByLetter("А").get(0).getQuestion());

        repository.setQuestions("А", 0, "Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий");
    }

    @Test
    void setAnswers() {
        List<QuestionEntity> result = repository.getQuestionAnswerByLetter("Ъ");
        repository.setAnswers("Ъ", result.size() - 1, "ответ из теста " + result.size());
        Assertions.assertEquals(
                "ОТВЕТ ИЗ ТЕСТА " + result.size(),
                repository.getQuestionAnswerByLetter("Ъ").get(result.size() - 1).getAnswer()
        );
    }
}