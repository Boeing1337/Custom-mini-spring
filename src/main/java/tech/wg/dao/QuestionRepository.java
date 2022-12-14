package tech.wg.dao;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@Component
@NoArgsConstructor
public class QuestionRepository {
    public static final String ANY_NEW_LINE = "\\n|\\r\\n";
    public static final String SIMPLE_NEW_LINE = "\n";
    private static final String QUESTIONS_BLOCK_SEPARATOR = ";(\\r?\\n){2}";
    @InjectProperty("questionsFileName")
    public static String QUESTIONS_FILE_NAME;
    private final Map<String, List<QuestionEntity>> questionByLetters = new HashMap<>();

    private void reading() {
        questionByLetters.clear();
        try {
            String[] temp = Files.readString(Paths.get(QUESTIONS_FILE_NAME))
                    .replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE)
                    .split(QUESTIONS_BLOCK_SEPARATOR);
            for (String rawData : temp) {
                List<QuestionEntity> questionsWithOneLetter = new ArrayList<>();
                String[] rawQuestions = rawData.trim().split("/");
                for (int k = 1; k < rawQuestions.length - 1; k += 2) {
                    QuestionEntity question = new QuestionEntity(rawQuestions[k].trim(), rawQuestions[k + 1].trim());
                    questionsWithOneLetter.add(question);
                }
                questionByLetters.put(rawQuestions[0].substring(0, 1), questionsWithOneLetter);
            }
        } catch (IOException e) {
            log.warn(e);
        }
    }

    private void updateQuestion() {
        try (FileWriter writerFile = new FileWriter("." + separator + QUESTIONS_FILE_NAME, UTF_8)) {
            for (String key : questionByLetters.keySet()) {
                writerFile.write(key.toUpperCase() + ":\n");
                for (QuestionEntity question : questionByLetters.get(key)) {
                    writerFile.write("/" + question.getQuestion() + "/\n");
                    writerFile.write(question.getAnswer() + "\n");
                }
                writerFile.write(";\n\n");
            }
        } catch (IOException e) {
            log.warn(e);
        }
        reading();
    }

    public List<QuestionEntity> getQuestionAnswerByLetter(String letter) {
        reading();
        return new ArrayList<>(questionByLetters.get(letter));
    }

    public void addQuestionAnswers(String question, String answer) {
        reading();
        QuestionEntity newQuestion = new QuestionEntity(question, answer);
        String key = answer.toUpperCase().substring(0, 1);
        if (questionByLetters.containsKey(key)) {
            questionByLetters.get(key).add(newQuestion);
        }
        updateQuestion();
    }

    public void setQuestions(String letter, int index, String newQuestions) {
        reading();
        questionByLetters.computeIfPresent(letter.toUpperCase(), (s, questionEntities) -> {
            if (!(questionEntities.size() <= index || index < 0)) {
                questionEntities.get(index).setQuestion(newQuestions);
            }
            return questionEntities;
        });
        updateQuestion();
    }

    public void setAnswers(String letter, int index, String newAnswer) {
        reading();
        questionByLetters.computeIfPresent(letter.toUpperCase(), (s, questionEntities) -> {
            if (!(questionEntities.size() <= index || index < 0)) {
                questionEntities.get(index).setAnswer(newAnswer.toUpperCase());
            }
            return questionEntities;
        });
        updateQuestion();
    }

    /**
     * Удаляет вопрос с ответом но только в том случае если Запись в файл основывается на длинне массива Questions
     *
     * @param letter буква по которой удаляем вопрос
     * @param index  позиция вопроса начиная с 0
     */
    public void deleteQuestions(String letter, int index) {
        reading();
        questionByLetters.computeIfPresent(letter, (s, questionEntities) -> {
            if (!(questionEntities.size() <= index || index < 0)) {
                questionEntities.remove(index);
            }
            return questionEntities;
        });
        updateQuestion();
    }

}
