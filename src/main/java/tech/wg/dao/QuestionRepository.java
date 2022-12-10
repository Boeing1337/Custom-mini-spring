package tech.wg.dao;

import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
public class QuestionRepository {
    public static final String ANY_NEW_LINE = "\\n|\\r\\n";
    public static final String SIMPLE_NEW_LINE = "\n";
    private static final String QUESTIONS_BLOCK_SEPARATOR = ";(\\r?\\n){2}";
    private final ArrayList<List<String>> questions = new ArrayList<>();
    private final ArrayList<List<String>> answers = new ArrayList<>();
    private String[] letters;

    private void reading() {
        try {
            String[] temp = Files.readString(Paths.get("abc and questions"))
                    .replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE)
                    .split(QUESTIONS_BLOCK_SEPARATOR);
            answers.clear();
            questions.clear();
            letters = new String[temp.length];

            for (int i = 0; i < temp.length; i++) {
                String s = temp[i];

                letters[i] = temp[i].substring(0, 1);

                String[] rawAnswers = s.trim().split("/");
                List<String> listQuestions = new ArrayList<>();
                List<String> listAnswers = new ArrayList<>();
                for (int j = 1; j < rawAnswers.length; j += 2) {
                    listQuestions.add(rawAnswers[j].trim());
                    listAnswers.add(rawAnswers[j + 1].toUpperCase().trim());
                }
                questions.add(listQuestions);
                answers.add(listAnswers);
            }
        } catch (IOException e) {
            log.warn(e);
        }
    }


    public List<List<String>> getQuestions() {
        reading();
        return new ArrayList<>(questions);
    }

    public List<List<String>> getAnswers() {
        reading();
        return new ArrayList<>(answers);
    }


    private void updateQuestion() {
        reading();
        try (FileWriter writerFile = new FileWriter("." + separator + "abc and questions", UTF_8)) {
            for (int i = 0; i < letters.length; i++) {
                writerFile.write(letters[i].charAt(0) + ":\n");
                for (int j = 0; j < questions.get(i).size(); j++) {
                    writerFile.write("/" + questions.get(i).get(j) + "/\n");
                    writerFile.write(answers.get(i).get(j).toUpperCase() + "\n");
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
        ArrayList<QuestionEntity> result = new ArrayList<>();
        for (int letterNum = 0; letterNum < letters.length; letterNum++) {
            String s = letters[letterNum];
            if (s.trim().charAt(0) == letter.trim().toUpperCase().charAt(0)) {
                for (int qNum = 0; qNum < questions.get(letterNum).size(); qNum++) {
                    result.add(new QuestionEntity(
                            questions.get(letterNum).get(qNum)
                            , answers.get(letterNum).get(qNum)
                    ));
                }
                break;
            }
        }
        return result;
    }

    public void addQuestionAnswers(String question, String answer) {
        reading();
        for (int i = 0; i < letters.length; i++) {
            if (answer.toUpperCase().charAt(0) == letters[i].toUpperCase().charAt(0)) {
                questions.get(i).add(question);
                answers.get(i).add(answer.toUpperCase());
            }
        }
        updateQuestion();
    }

    public void setQuestions(String letter, int index, String newQuestions) {
        reading();
        for (int i = 0; i < letters.length; i++) {
            if (letter.toUpperCase().charAt(0) == letters[i].toUpperCase().charAt(0)) {
                questions.get(i).set(index, newQuestions);
                break;
            }
        }
        updateQuestion();
    }

    public void setAnswers(String letter, int index, String newQuestions) {
        reading();
        for (int i = 0; i < letters.length; i++) {
            if (letter.toUpperCase().charAt(0) == letters[i].toUpperCase().charAt(0)) {
                answers.get(i).set(index, newQuestions);
                break;
            }
        }
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
        for (int i = 0; i < letters.length; i++) {
            if (letter.toUpperCase().charAt(0) == letters[i].toUpperCase().charAt(0)) {
                questions.get(i).remove(index);
                break;
            }
        }
        updateQuestion();
    }

}
