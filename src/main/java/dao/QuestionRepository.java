package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;

public class QuestionRepository {


    private final ArrayList<List<String>> questions = new ArrayList<>();


    private final ArrayList<List<String>> answers = new ArrayList<>();
    private String[] content;

    public QuestionRepository() {
        reading();
        getAllQuestions();
        getAllAnswers();
    }

    private void reading() {
        try {
            content = Files.readString(Paths.get("abc and questions"))
                    .split(";(\\r?\\n){2}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> getQuestions() {
        return new ArrayList<>(questions);
    }

    public List<List<String>> getAnswers() {
        return new ArrayList<>(answers);
    }

    private void getAllAnswers() {
        answers.clear();
        for (String s : content) {
            String[] splitAnswers = s.trim().split("/");
            List<String> listAnswers = new ArrayList<>();
            for (int j = 2; j < splitAnswers.length; j += 2) {
                listAnswers.add(splitAnswers[j].toUpperCase().trim());
            }
            answers.add(listAnswers);
        }
    }

    private void getAllQuestions() {
        questions.clear();
        for (String s : content) {
            String[] splitQuestions = s.trim().split("/");
            List<String> listQuestions = new ArrayList<>();
            for (int j = 1; j < splitQuestions.length; j += 2) {
                listQuestions.add(splitQuestions[j].trim());
            }
            questions.add(listQuestions);
        }
    }


    private void updateQuestion() {
        try (FileWriter writerFile = new FileWriter("." + separator + "abc and questions", UTF_8)) {
            for (int i = 0; i < content.length; i++) {
                writerFile.write(content[i].charAt(0) + ":\n");
                for (int j = 0; j < questions.get(i).size(); j++) {
                    writerFile.write("/" + questions.get(i).get(j) + "/\n");
                    writerFile.write(answers.get(i).get(j).toUpperCase() + "\n");
                }
                writerFile.write(";\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reading();
    }

    public List<String> getQuestionAnswerByLetter(String letter) {
        ArrayList<String> a = new ArrayList<>();
        for (String s : content) {
            if (s.trim().charAt(0) == letter.trim().toUpperCase().charAt(0)) {
                String[] quest = s.trim().split("/");
                for (String value : quest) {
                    a.add(value.trim());
                }
            }
        }
        return a;
    }

    public void addQuestionAnswers(String question, String answer) {
        for (int i = 0; i < content.length; i++) {
            if (answer.toUpperCase().charAt(0) == content[i].toUpperCase().charAt(0)) {
                questions.get(i).add(question);
                answers.get(i).add(answer.toUpperCase());
            }
        }
        updateQuestion();
    }

    public void setQuestions(String letter, int index, String newQuestions) {
        for (int i = 0; i < content.length; i++) {
            if (letter.toUpperCase().charAt(0) == content[i].toUpperCase().charAt(0)) {
                questions.get(i).set(index, newQuestions);
            }
        }
        updateQuestion();
    }

    public void setAnswers(String letter, int index, String newQuestions) {
        for (int i = 0; i < content.length; i++) {
            if (letter.toUpperCase().charAt(0) == content[i].toUpperCase().charAt(0)) {
                answers.get(i).set(index, newQuestions);
            }
        }
        updateQuestion();
    }

    public void deleteQuestions(String letter, int index) {
        for (int i = 0; i < content.length; i++) {
            if (letter.toUpperCase().charAt(0) == content[i].toUpperCase().charAt(0)) {
                questions.get(i).remove(index);
            }
        }
        updateQuestion();
    }

    public void deleteAnswers(String letter, int index) {
        for (int i = 0; i < content.length; i++) {
            if (letter.toUpperCase().charAt(0) == content[i].toUpperCase().charAt(0)) {
                answers.get(i).remove(index);
            }
        }
        updateQuestion();
    }
}
