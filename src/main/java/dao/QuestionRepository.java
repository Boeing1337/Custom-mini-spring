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
    private String[] bukvi;

    public QuestionRepository() {
        reading();
    }

    private void reading() {
        try {
            bukvi = Files.readString(Paths.get("abc and questions"))
                    .split(";(\\r?\\n){2}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getBukvi() {
        return bukvi;
    }

    public List<List<String>> getAllAnswers() {
        answers.clear();
        for (String s : bukvi) {
            String[] splitAnswers = s.trim().split("/");
            List<String> listAnswers = new ArrayList<>();
            for (int j = 2; j < splitAnswers.length; j += 2) {
                listAnswers.add(splitAnswers[j].trim());
            }
            answers.add(listAnswers);
        }
        return answers;
    }

    public List<List<String>> getAllQuestions() {
        questions.clear();
        for (String s : bukvi) {
            String[] splitQuestions = s.trim().split("/");
            List<String> listQuestions = new ArrayList<>();
            for (int j = 1; j < splitQuestions.length; j += 2) {
                listQuestions.add(splitQuestions[j].trim());
            }
            questions.add(listQuestions);
        }
        return questions;
    }


    private void updateQuestion() {
        try (FileWriter writerFile = new FileWriter("." + separator + "abc and questions", UTF_8)) {
            for (int i = 0; i < bukvi.length; i++) {
                writerFile.write(bukvi[i].charAt(0) + ":\n");
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

    public void addQuestionAnswers(String question, String answer) {
        reading();
        getAllQuestions();
        getAllAnswers();
        for (int i = 0; i < bukvi.length; i++) {
            if (answer.charAt(0) == bukvi[i].charAt(0)) {
                questions.get(i).add(question);
                answers.get(i).add(answer.toUpperCase());
            }
        }
        updateQuestion();
    }

    public List<String> getQuestionBy(String letter) {
        reading();
        ArrayList<String> a = new ArrayList<>();
        for (String s : bukvi) {
            if (s.trim().charAt(0) == letter.trim().toUpperCase().charAt(0)) {
                String[] quest = s.trim().split("/");
                for (String value : quest) {
                    a.add(value.trim());
                }
            }
        }
        return a;
    }
}