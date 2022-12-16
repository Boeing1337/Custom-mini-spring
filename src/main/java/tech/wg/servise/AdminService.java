package tech.wg.servise;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionEntity;
import tech.wg.dao.QuestionRepository;
import tech.wg.tools.Grammar;

import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class AdminService {
    private final String except = "\nНеверная команда.\n";
    @InjectObject
    private QuestionRepository questionRepository;
    @InjectObject
    private KeywordsRepository keywordsRepository;
    @InjectObject
    private Grammar grammar;

    public void action() {
        do {
            grammar.print("\n-----Категория файлов-----\n" +
                    "1) Загаданные слова.\n" +
                    "2) Вопросы и ответы.\n" +
                    "0) Выход из Админ-панели.\n" +
                    "Выберете вариант: ");
            switch (grammar.nextLine()) {
                case "1":
                    menuWordsOperation();
                    break;
                case "2":
                    menuQuestsAnswers();
                    break;
                case "0":
                    return;
                default:
                    grammar.println(except);
                    break;
            }
        } while (true);
    }

    private void menuWordsOperation() {
        try {
            do {
                grammar.print("\n-----Работа с загаданными словами-----\n" +
                        "1) Прочитать все загаданные слова.\n" +
                        "2) Добавить слово.\n" +
                        "3) Изменит слово.\n" +
                        "4) Удалить слово.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ");
                switch (grammar.nextLine()) {
                    case "1":
                        grammar.println("");
                        List<String> listMenuWords = keywordsRepository.readKeywords();
                        for (String s : listMenuWords) {
                            grammar.println(s);
                        }
                        break;
                    case "2":
                        grammar.print("Введите новое слово или введите 0 для выхода: ");
                        String input = grammar.nextLine().toUpperCase();
                        if (input.equals("0")) {
                            break;
                        }
                        keywordsRepository.addKeywords(Collections.singleton(input));
                        break;
                    case "3":
                        grammar.print("Введите слово которое хотите изменить или введите 0 для выхода: ");
                        String inputWord = grammar.nextLine().toUpperCase();
                        if (inputWord.equals("0")) {
                            break;
                        }
                        grammar.println("Введите новое слово или введите 0 для выхода");
                        String inputNewWord = grammar.nextLine().toUpperCase();
                        if (inputNewWord.equals("0")) {
                            break;
                        }
                        keywordsRepository.editKeywords(inputWord, inputNewWord);
                        break;
                    case "4":
                        grammar.print("Введите слово которое хотите удалить или введите 0 для выхода: ");
                        String inputDeleteWord = grammar.nextLine().toUpperCase();
                        if (inputDeleteWord.equals("0")) {
                            break;
                        }
                        keywordsRepository.deleteKeyword(inputDeleteWord);
                        break;
                    case "0":
                        return;
                    default:
                        grammar.println(except);
                        break;
                }
            } while (true);
        } catch (Exception e) {
            log.error(e);
            grammar.println("\nНеправильный ввод.\n");
        }
    }

    private void menuQuestsAnswers() {
        try {
            boolean flag = true;
            do {
                grammar.print("\n-----Работа с вопросами и ответами-----\n" +
                        "1) Прочитать все вопросы и ответы на выбранную букву.\n" +
                        "2) Добавить вопрос и ответ.\n" +
                        "3) Изменить вопрос.\n" +
                        "4) Изменить ответ.\n" +
                        "5) Удалить вопрос и ответ.\n" +
                        "0) назад.\n" +
                        "Выберете вариант: ");
                switch (grammar.nextLine()) {
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
                        grammar.println(except);
                        break;
                }
            } while (flag);
        } catch (Exception e) {
            grammar.println("\nНеправильный ввод.\n");
        }
    }

    private void printQuestsAnswers() {
        grammar.print("Введите букву русского алфавита или введите 0 для выхода: ");
        String later = grammar.nextLine().toUpperCase();
        if (later.equals("0")) {
            return;
        }
        List<QuestionEntity> listPrintQuestAnswer = questionRepository.getQuestionAnswerByLetter(later);
        if (listPrintQuestAnswer.isEmpty()) {
            grammar.println("\n\nНичего на эту букву нет.");
        }
        grammar.println("");
        for (int i = 0; i < listPrintQuestAnswer.size(); i++) {
            grammar.println(i + 1 + ") " + listPrintQuestAnswer.get(i).getQuestion());
            grammar.println(i + 1 + ") " + listPrintQuestAnswer.get(i).getAnswer());
        }
    }

    private void addQuestAnswer() {
        grammar.print("Введите вопрос или введите 0 для выхода: ");
        String questAddQuestAnswer = grammar.nextLine().trim();
        if (questAddQuestAnswer.equals("0")) {
            return;
        }
        grammar.println("");
        grammar.print("Введите ответ или введите 0 для выхода: ");
        String answerAddQuestAnswer = grammar.nextLine();
        if (answerAddQuestAnswer.equals("0")) {
            return;
        }
        questionRepository.addQuestionAnswers(questAddQuestAnswer, answerAddQuestAnswer);
    }

    private void changeQuest() {
        grammar.print("Какая первая буква ответа на вопрос который хотите изменить или введите 0 для выхода: ");
        String wordChangeQuest = grammar.nextLine().toUpperCase();
        if (wordChangeQuest.equals("0")) {
            return;
        }
        grammar.println("");
        grammar.println("");
        List<QuestionEntity> listChangeQuest = questionRepository.getQuestionAnswerByLetter(wordChangeQuest);
        if (listChangeQuest.isEmpty()) {
            grammar.println("Ничего нет на эту букву.\n");
            return;
        }
        for (int i = 0; i < listChangeQuest.size(); i++) {
            grammar.println(i + 1 + ") " + listChangeQuest.get(i).getQuestion());
        }
        while (true) {
            grammar.print("Выберете вариант вопроса или введите 0 для выхода: ");
            if (grammar.hasNextInt()) {
                int number;
                while (true) {
                    number = Integer.parseInt(grammar.nextLine().trim());
                    if (number == 0) {
                        return;
                    } else if (number <= listChangeQuest.size() && number > 0) {
                        break;
                    } else {
                        grammar.println("Не верный ввод.");
                    }
                }
                grammar.println("");
                grammar.println("Введите новый вопрос в одну строку или введите 0 для выхода: ");
                String questChangeQuest = grammar.nextLine();
                if (questChangeQuest.equals("0"))
                    return;
                questionRepository.setQuestions(wordChangeQuest, number - 1, questChangeQuest);
                break;
            } else {
                grammar.println("\nНеправильный ввод.\n");
            }
        }
    }

    private void changeAnswer() {
        grammar.print("Какая первая буква ответа или введите 0 для выхода: ");
        String wordChangeAnswer = grammar.nextLine().toUpperCase();
        if (wordChangeAnswer.equals("0")) {
            return;
        }
        List<QuestionEntity> listChangeAnswer = questionRepository.getQuestionAnswerByLetter(wordChangeAnswer);
        if (listChangeAnswer.isEmpty()) {
            grammar.println("\nНичего нет на эту букву.");
            return;
        }
        grammar.println("");
        for (int i = 0; i < listChangeAnswer.size(); i++) {
            grammar.println(i + 1 + ") " + listChangeAnswer.get(i).getAnswer());
        }
        while (true) {
            grammar.print("Выберете вариант ответа или введите 0 для выхода: ");
            if (grammar.hasNextInt()) {
                int optionChangeAnswer;
                while (true) {
                    optionChangeAnswer = Integer.parseInt(grammar.nextLine().trim());
                    if (optionChangeAnswer == 0) {
                        return;
                    } else if (optionChangeAnswer <= listChangeAnswer.size() && optionChangeAnswer > 0) {
                        break;
                    } else {
                        grammar.println("Не верный ввод.");
                    }
                }
                grammar.println("");
                grammar.print("Введите новый ответ на выбранную букву или введите 0 для выхода: ");
                String newAnswer = grammar.nextLine().toUpperCase();
                if (newAnswer.equals("0"))
                    return;
                questionRepository.setAnswers(wordChangeAnswer, optionChangeAnswer - 1, newAnswer);
                break;
            } else {
                grammar.println("\nНеправильный ввод.\n");
            }
        }
    }

    private void deleteQuestAnswer() {
        grammar.print("Введите букву русского алфавита или введите 0 для выхода: ");
        String wordDeleteQuestAnswer = grammar.nextLine().toUpperCase();
        if (wordDeleteQuestAnswer.equals("0")) {
            return;
        }
        List<QuestionEntity> listDeleteQuestAnswer = questionRepository.getQuestionAnswerByLetter(wordDeleteQuestAnswer);
        grammar.println("");
        if (listDeleteQuestAnswer.isEmpty()) {
            grammar.println("Ничего нет на эту букву.");
            return;
        }
        for (int i = 0; i < listDeleteQuestAnswer.size(); i++) {
            grammar.println(i + 1 + ") " + listDeleteQuestAnswer.get(i).getQuestion());
            grammar.println(i + 1 + ") " + listDeleteQuestAnswer.get(i).getAnswer());
        }
        while (true) {
            grammar.print("Выберете вариант или введите 0 для выхода: ");
            if (grammar.hasNextInt()) {
                int optionDeleteQuestAnswer;
                while (true) {
                    optionDeleteQuestAnswer = Integer.parseInt(grammar.nextLine().trim());
                    if (optionDeleteQuestAnswer == 0) {
                        return;
                    } else if (optionDeleteQuestAnswer <= listDeleteQuestAnswer.size() && optionDeleteQuestAnswer > 0) {
                        break;
                    } else {
                        grammar.println("Не верный ввод.");
                    }
                }
                questionRepository.deleteQuestions(wordDeleteQuestAnswer, optionDeleteQuestAnswer - 1);
                break;
            } else {
                grammar.println("\nНеправильный ввод.\n");
            }
        }
    }
}
