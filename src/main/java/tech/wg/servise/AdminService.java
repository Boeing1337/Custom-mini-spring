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
            switch (grammar.readLine()) {
                case "1":
                    menuWordsOperation();
                    break;
                case "2":
                    menuQuestsAnswers();
                    break;
                case "0":
                    return;
                default:
                    grammar.write(except);
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
                switch (grammar.readLine()) {
                    case "1":
                        grammar.write("");
                        List<String> listMenuWords = keywordsRepository.readKeywords();
                        for (String s : listMenuWords) {
                            grammar.write(s);
                        }
                        break;
                    case "2":
                        grammar.print("Введите новое слово: ");
                        keywordsRepository.addKeywords(Collections.singleton(grammar.readLine().toUpperCase()));
                        break;
                    case "3":
                        grammar.print("Введите слово которое хотите изменить: ");
                        String inputWord = grammar.readLine().toUpperCase();
                        grammar.write("Введите новое слово");
                        String inputNewWord = grammar.readLine().toUpperCase();
                        keywordsRepository.editKeywords(inputWord, inputNewWord);
                        break;

                    case "4":
                        grammar.print("Введите слово которое хотите удалить: ");
                        keywordsRepository.deleteKeyword(grammar.readLine().toUpperCase());
                        break;
                    case "0":
                        return;
                    default:
                        grammar.write(except);
                        break;
                }
            } while (true);
        } catch (Exception e) {
            log.error(e);
            grammar.write("\nНеправильный ввод.\n");
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
                switch (grammar.readLine()) {
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
                        grammar.write(except);
                        break;
                }
            } while (flag);
        } catch (Exception e) {
            grammar.write("\nНеправильный ввод.\n");
        }
    }

    private void printQuestsAnswers() {
        grammar.print("Введите букву русского алфавита: ");
        List<QuestionEntity> listPrintQuestAnswer = questionRepository.getQuestionAnswerByLetter(grammar.readLine().toUpperCase());
        grammar.write("");
        for (int i = 0; i < listPrintQuestAnswer.size(); i++) {
            grammar.write(i + 1 + ") " + listPrintQuestAnswer.get(i).getQuestion());
            grammar.write(i + 1 + ") " + listPrintQuestAnswer.get(i).getAnswer());
        }
    }

    private void addQuestAnswer() {
        grammar.print("Введите вопрос: ");
        String questAddQuestAnswer = grammar.readLine().trim();
        grammar.write("");
        grammar.print("Введите ответ: ");
        String answerAddQuestAnswer = grammar.readLine();
        questionRepository.addQuestionAnswers(questAddQuestAnswer, answerAddQuestAnswer);
    }

    private void changeQuest() {
        grammar.print("Какая первая буква ответа на вопрос который хотите изменить: ");
        String wordChangeQuest = grammar.readLine();
        grammar.write("");
        grammar.write("");
        List<QuestionEntity> listChangeQuest = questionRepository.getQuestionAnswerByLetter(wordChangeQuest);
        for (int i = 0; i < listChangeQuest.size(); i++) {
            grammar.write(i + 1 + ") " + listChangeQuest.get(i).getQuestion());
        }
        while (true) {
            grammar.print("Выберете вариант вопроса: ");
            if (grammar.hasNextInt()) {
                int number = Integer.parseInt(grammar.readLine().trim()) - 1;
                grammar.write("");
                grammar.write("Введите новый вопрос в одну строку: ");
                String questChangeQuest = grammar.readLine();
                questionRepository.setQuestions(wordChangeQuest, number, questChangeQuest);
                break;
            } else {
                grammar.write("\nНеправильный ввод.\n");
            }
        }
    }

    private void changeAnswer() {
        grammar.print("Какая первая буква ответа: ");
        String wordChangeAnswer = grammar.readLine();
        List<QuestionEntity> listChangeAnswer = questionRepository.getQuestionAnswerByLetter(wordChangeAnswer);
        grammar.write("");
        for (int i = 0; i < listChangeAnswer.size(); i++) {
            grammar.write(i + 1 + ") " + listChangeAnswer.get(i).getAnswer());
        }
        while (true) {
            grammar.print("Выберете вариант ответа: ");
            if (grammar.hasNextInt()) {
                int optionChangeAnswer = Integer.parseInt(grammar.readLine().trim()) - 1;
                grammar.write("");
                grammar.print("Введите новый ответ на выбранную букву: ");
                String newAnswer = grammar.readLine();
                questionRepository.setAnswers(wordChangeAnswer, optionChangeAnswer, newAnswer);
                break;
            } else {
                grammar.write("\nНеправильный ввод.\n");
            }
        }
    }

    private void deleteQuestAnswer() {
        grammar.print("Введите букву русского алфавита: ");
        String wordDeleteQuestAnswer = grammar.readLine();
        List<QuestionEntity> listDeleteQuestAnswer = questionRepository.getQuestionAnswerByLetter(wordDeleteQuestAnswer);
        grammar.write("");
        for (int i = 0; i < listDeleteQuestAnswer.size(); i++) {
            grammar.write(i + 1 + ") " + listDeleteQuestAnswer.get(i).getQuestion());
            grammar.write(i + 1 + ") " + listDeleteQuestAnswer.get(i).getAnswer());
        }
        while (true) {
            grammar.print("Выберете вариант: ");
            if (grammar.hasNextInt()) {
                int optionDeleteQuestAnswer = Integer.parseInt(grammar.readLine().trim()) - 1;
                questionRepository.deleteQuestions(wordDeleteQuestAnswer, optionDeleteQuestAnswer);
                break;
            } else {
                grammar.write("\nНеправильный ввод.\n");
            }
        }
    }
}
