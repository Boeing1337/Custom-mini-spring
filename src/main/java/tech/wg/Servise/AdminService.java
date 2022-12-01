package tech.wg.Servise;

import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    private final QuestionRepository questionRepository = new QuestionRepository();
    private final KeywordsRepository keywordsRepository = new KeywordsRepository();
    private final Scanner scanner = new Scanner(System.in);
    private final String except = "Неверная команда.";

    public AdminService() {
        action();
    }

    public void action() {
        boolean flag = true;
        do {
            System.out.print("\n-----Категория файлов-----\n" +
                    "1) Загаданные слова.\n" +
                    "2) Вопросы и ответы.\n" +
                    "0) Выход из Админ-панели.\n" +
                    "Выберете вариант: ");

            switch (scanner.nextLine()) {
                case "1":
                    menuWordsOperation();
                    break;
                case "2":
                    menuQuestsAnswers();
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    System.out.println(except);
                    break;
            }
        } while (flag);
        scanner.close();
    }

    private void menuWordsOperation() {
        try {
            boolean flag = true;
            do {
                System.out.print("\n-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ");

                switch (scanner.nextLine()) {
                    case "1":
                        List<String> listMenuWords = keywordsRepository.readKeywords();
                        for (String s : listMenuWords) {
                            System.out.println(s);
                        }
                        break;
                    case "2":
                        System.out.print("Введите новое слово: ");
                        keywordsRepository.addKeywords(Collections.singleton(scanner.nextLine().toUpperCase()));
                        break;
                    case "3":
                        System.out.print("Введите слово которое хотите изменить: ");
                        String inputWord = scanner.nextLine().toUpperCase();
                        System.out.println("Введите новое слово");
                        String inputNewWord = scanner.nextLine().toUpperCase();
                        keywordsRepository.editKeywords(inputWord, inputNewWord);
                        break;

                    case "4":
                        System.out.print("Введите слово которое хотите удалить: ");
                        keywordsRepository.deleteKeyword(scanner.nextLine().toUpperCase());
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println(except);
                        break;
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
                System.out.print("\n-----Работа с вопросами и ответами-----\n" +
                        "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                        "2) Добавить вопрос и ответ.\n" +
                        "3) Изменить вопрос.\n" +
                        "4) Изменить ответ.\n" +
                        "5) Удалить вопрос и ответ.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ");
                switch (scanner.nextLine()) {
                    case "1":
                        printQuestsAnswers();
                        break;
                    case "2":
                        addQuestAnswer();
                        break;
                    case "3":
                        changeQuest();
                        break;
                    case "4":
                        changeAnswer();
                        break;
                    case "5":
                        deleteQuestAnswer();
                        break;
                    case "0":
                        flag = false;
                        break;
                    default:
                        System.out.println(except);
                        break;
                }
            } while (flag);
        } catch (Exception e) {
            System.out.println("Неправильный ввод");
        }
    }

    private void printQuestsAnswers() {
        System.out.print("Введите букву русского алфавита: ");
        List<String> listPrintQuestAnswer = questionRepository.getQuestionAnswerByLetter(scanner.nextLine());
        System.out.println();
        int numPrintQuestAnswer = 1;
        for (int i = 1; i < listPrintQuestAnswer.size(); i++) {
            System.out.println(numPrintQuestAnswer + ") " + listPrintQuestAnswer.get(i));
            if (i % 2 == 0) {
                numPrintQuestAnswer++;
            }
        }
    }

    private void addQuestAnswer() {
        System.out.print("Введите вопрос: ");
        String questAddQuestAnswer = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Введите ответ: ");
        String answerAddQuestAnswer = scanner.nextLine();
        questionRepository.addQuestionAnswers(questAddQuestAnswer, answerAddQuestAnswer);
    }

    private void changeQuest() {
        System.out.print("Какая первая буква ответа на вопрос который хотите изменить: ");
        String wordChangeQuest = scanner.nextLine();
        System.out.println();
        List<String> listChangeQuest = questionRepository.getQuestionAnswerByLetter(wordChangeQuest);
        System.out.println();
        int numChangeQuest = 1;
        for (int i = 1; i < listChangeQuest.size(); i = i + 2) {
            System.out.println(numChangeQuest + ") " + listChangeQuest.get(i));
            numChangeQuest++;
        }
        while (true) {
            System.out.print("Выберете вариант вопроса: ");
            if (scanner.hasNextInt()) {
                int number = Integer.parseInt(scanner.nextLine().trim()) - 1;
                System.out.println();
                System.out.println("Введите новый вопрос в одну строку: ");
                String questChangeQuest = scanner.nextLine();
                questionRepository.setQuestions(wordChangeQuest, number, questChangeQuest);
                break;
            } else {
                System.out.println("\nНе правильный ввод.\n");
            }
        }
    }

    private void changeAnswer() {
        System.out.print("Какая первая буква ответа: ");
        String wordChangeAnswer = scanner.nextLine();
        List<String> listChangeAnswer = questionRepository.getQuestionAnswerByLetter(wordChangeAnswer);
        System.out.println();
        int numChangeAnswer = 1;
        for (int i = 2; i < listChangeAnswer.size(); i = i + 2) {
            System.out.println(numChangeAnswer + ") " + listChangeAnswer.get(i));
            numChangeAnswer++;
        }
        while (true) {
            System.out.print("Выберете вариант ответа: ");

            if (scanner.hasNextInt()) {
                int optionChangeAnswer = Integer.parseInt(scanner.nextLine().trim()) - 1;
                System.out.println();
                System.out.print("Введите новый ответ на выбранную букву: ");
                String newAnswer = scanner.nextLine();
                questionRepository.setAnswers(wordChangeAnswer, optionChangeAnswer, newAnswer);
                break;
            } else {
                System.out.println("\nНе правильный ввод.\n");
            }
        }
    }

    private void deleteQuestAnswer() {
        System.out.print("Введите букву русского алфавита: ");
        String wordDeleteQuestAnswer = scanner.nextLine();
        List<String> listDeleteQuestAnswer = questionRepository.getQuestionAnswerByLetter(wordDeleteQuestAnswer);
        System.out.println();
        int countDeleteQuestAnswer = 1;
        for (int i = 1; i < listDeleteQuestAnswer.size(); i++) {
            System.out.println(countDeleteQuestAnswer + ") " + listDeleteQuestAnswer.get(i));
            if (i % 2 == 0) {
                countDeleteQuestAnswer++;
            }
        }
        while (true) {
            System.out.print("Выберете вариант: ");
            if (scanner.hasNextInt()) {
                int optionDeleteQuestAnswer = Integer.parseInt(scanner.nextLine().trim()) - 1;
                questionRepository.deleteQuestions(wordDeleteQuestAnswer, optionDeleteQuestAnswer);
                questionRepository.deleteAnswers(wordDeleteQuestAnswer, optionDeleteQuestAnswer);
                break;
            } else {
                System.out.println("\nНе правильный ввод.\n");
            }
        }
    }
}
