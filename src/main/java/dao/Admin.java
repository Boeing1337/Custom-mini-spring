package dao;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Admin {
    static QuestionRepository questionRepository = new QuestionRepository();
    static KeywordsRepository keywordsRepository = new KeywordsRepository();
    static Scanner scanner = new Scanner(System.in);
    String aaaa = "Неверная команда.";

    public static void main(String[] args) {
        Admin admin = new Admin();
    }

    public Admin() {
        boolean flag = true;
        do {
            System.out.print("""

                    -----Категория файлов-----
                    1) Загаданные слова.
                    2) Вопросы и ответы.
                    0) Выход из Админ-панели.
                    Выберете вариант:\s""");

            switch (scanner.nextLine()) {
                case "1" -> menuWords();
                case "2" -> menuQuestsAnswers();
                case "0" -> flag = false;
                default -> System.out.println(aaaa);
            }
        } while (flag);
        scanner.close();
    }

    private void menuWords() {
        try {
            boolean flag = true;
            do {
                System.out.print("""

                        -----Работа с загаданными словами-----
                        1) Прочитать все загаданные слова.
                        2) Добавить слово.
                        3) Изменит слово.
                        4) Удалить слово.
                        0) назад.
                        Выберете вариант:\s""");

                switch (scanner.nextLine()) {
                    case "1" -> {
                        List<String> a = keywordsRepository.readKeywords();
                        for (String s : a) {
                            System.out.println(s);
                        }
                    }
                    case "2" -> {
                        System.out.print("Введите новое слово: ");
                        keywordsRepository.addKeywords(Collections.singleton(scanner.nextLine().toUpperCase()));
                    }
                    case "3" -> {
                        System.out.print("Введите слово которое хотите изменить: ");
                        String a1 = scanner.nextLine();
                        System.out.println("Введите новое слово");
                        String a2 = scanner.nextLine().toUpperCase();
                        keywordsRepository.editKeywords(a1, a2);
                    }
                    case "4" -> {
                        System.out.print("Введите слово которое хотите удалить: ");
                        keywordsRepository.deleteKeyword(scanner.nextLine().toUpperCase());
                    }
                    case "0" -> flag = false;
                    default -> System.out.println(aaaa);
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("Неправильный ввод");
        }
    }

    private void menuQuestsAnswers() {
        try {
            boolean flag = true;
            do {
                System.out.print("""

                        -----Работа с вопросами-----
                        1) Прочитать все вопросы и ответы на выбранную букву.
                        2) Добавить вопрос и ответ.
                        3) Изменить вопрос.
                        4) Изменить ответ.
                        5) Удалить вопрос и ответ.
                        0) назад.
                        Выберете вариант:\s""");
                switch (scanner.nextLine()) {
                    case "1" -> printQuestAnswer();
                    case "2" -> addQuestAnswer();
                    case "3" -> changeQuest();
                    case "4" -> changeAnswer();
                    case "5" -> deleteQuestAnswer();
                    case "0" -> flag = false;
                    default -> System.out.println(aaaa);
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("Неправильный ввод");
        }
    }

    private void printQuestAnswer() {
        System.out.print("Введите букву русского алфавита: ");
        List<String> a = questionRepository.getQuestionByLetter(scanner.nextLine());
        System.out.println();
        int num = 1;
        for (int i = 1; i < a.size(); i++) {
            System.out.println(num + ") " + a.get(i));
            if (i % 2 == 0) {
                num++;
            }
        }
    }

    private void addQuestAnswer() {
        System.out.print("Введите вопрос: ");
        String quest = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Введите ответ: ");
        String answer = scanner.nextLine();
        questionRepository.addQuestionAnswers(quest, answer);
    }

    private void changeQuest() {
        System.out.print("Какая первая буква ответа на вопрос который хотите изменить: ");
        String word = scanner.nextLine();
        System.out.println();
        List<String> que = questionRepository.getQuestionByLetter(word);
        System.out.println();
        int nume = 1;
        for (int i = 1; i < que.size(); i = i + 2) {
            System.out.println(nume + ") " + que.get(i));
            nume++;
        }
        System.out.print("Выберете вариант вопроса: ");
        int number = Integer.parseInt(scanner.nextLine().trim()) - 1;
        System.out.println();
        System.out.println("Введите новый вопрос в одну строку: ");
        String q = scanner.nextLine();
        questionRepository.setQuestions(word, number, q);
    }

    private void changeAnswer() {
        System.out.print("Какая первая буква ответа: ");
        String w = scanner.nextLine();
        List<String> qu = questionRepository.getQuestionByLetter(w);
        System.out.println();
        int n = 1;
        for (int i = 2; i < qu.size(); i = i + 2) {
            System.out.println(n + ") " + qu.get(i));
            n++;
        }
        System.out.print("Выберете вариант ответа: ");
        int numbe = Integer.parseInt(scanner.nextLine().trim()) - 1;
        System.out.println();
        System.out.print("Введите новый ответ: ");
        String qw = scanner.nextLine();
        questionRepository.setAnswers(w, numbe, qw);
    }

    private void deleteQuestAnswer() {
        System.out.print("Введите букву русского алфавита: ");
        String wq = scanner.nextLine();
        List<String> quq = questionRepository.getQuestionByLetter(wq);
        System.out.println();
        int nummm = 1;
        for (int i = 1; i < quq.size(); i++) {
            System.out.println(nummm + ") " + quq.get(i));
            if (i % 2 == 0) {
                nummm++;
            }
        }
        System.out.print("Выберете вариант: ");
        int nuberr = Integer.parseInt(scanner.nextLine().trim()) - 1;
        questionRepository.deleteQuestions(wq, nuberr);
        questionRepository.deleteAnswers(wq, nuberr);
    }
}
