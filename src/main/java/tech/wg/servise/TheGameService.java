package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionEntity;
import tech.wg.dao.QuestionRepository;
import tech.wg.dao.UserGameStateRepository;
import tech.wg.tools.Grammar;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class TheGameService {
    @InjectObject
    private KeywordsRepository wordsRepository;
    @InjectObject
    private QuestionRepository questionRepository;
    @InjectObject
    private UserGameStateRepository userGameStateRepository;
    @InjectObject
    private Grammar grammar;
    @InjectObject
    private ScoreService scoreService;

    private final Random random = new Random();
    private String wordToGuess;
    private char[] arrayRandomWord;
    private int[] guessedLetters;
    private boolean stopped = true;
    private int choosePersonLetter;
    private int back = 1;

    public void theGameContinue() {
        String progress = userGameStateRepository.getProgress(GlobalVariable.getCurrentUser().getLogin());
        if ("".equals(progress)) {
            grammar.println("Нет игры, которую можно продолжить. Начните новую игру");
            return;
        }
        String[] a = progress.trim().split(";");
        wordToGuess = a[0];
        arrayRandomWord = new char[wordToGuess.length()];
        guessedLetters = new int[wordToGuess.length()];
        for (int i = 0; i < arrayRandomWord.length; i++) {
            arrayRandomWord[i] = a[1].charAt(i);
            if (arrayRandomWord[i] != '*') {
                guessedLetters[i] = i + 1;
            }
        }
        back = 1;
        continuous();
    }

    public void theGameNew() {
        String progress = userGameStateRepository.getProgress(GlobalVariable.getCurrentUser().getLogin());
        if (!("".equals(progress))) {
            grammar.println(String.format("У вас есть незаконченная игра. Если вы начнете новую, будет защитано " +
                    "поражение%nВведите 1, чтоб начать новую игру%nВведите 0, чтоб вернуться назад"));
            boolean inTheProcess = true;
            while (inTheProcess) {
                String input = grammar.nextLine();
                switch (input) {
                    case "1":
                        grammar.println("Ваш счет изменился: " + scoreService.commitWinLoose(-1) + " очков");
                        userGameStateRepository.deleteProgress(GlobalVariable.getCurrentUser().getLogin());
                        back = 1;
                        gameBegin();
                        inTheProcess = false;
                        break;
                    case "0":
                        inTheProcess = false;
                        break;
                    default:
                        grammar.println("Нет такой команды. Попробуйте ещё раз");
                        break;
                }
            }
            return;
        }
        back = 1;
        gameBegin();
    }

    private void continuous() {
        while (back == 1) {
            stopped = true;
            startGame();
            if (back == 1) {
                isGameFinished();
            }
        }
        if (stopped) {
            grammar.println("Поздравляю, вы набрали " + scoreService.commitWinLoose(1) + " очков");
            userGameStateRepository.deleteProgress(GlobalVariable.getCurrentUser().getLogin());
        }
    }

    private String chooseWordToGuess() {
        List<String> myWords = wordsRepository.readKeywords();
        int randomIndexWord = random.nextInt(myWords.size());
        wordToGuess = myWords.get(randomIndexWord);
        return wordToGuess;
    }

    private void gameBegin() {
        back = 1;
        arrayRandomWord = new char[chooseWordToGuess().length()];
        Arrays.fill(arrayRandomWord, 0, wordToGuess.length(), '*');
        guessedLetters = new int[wordToGuess.length()];
        userGameStateRepository.writeProgress(GlobalVariable.getCurrentUser().getLogin(), wordToGuess, arrayRandomWord);
        continuous();
    }

    private void startGame() {
        while (stopped) {
            grammar.println(Arrays.toString(arrayRandomWord));
            boolean retern = true;
            grammar.println("Выбери номер буквы или нажми 0, чтобы вернуться назад");
            if (!grammar.hasNextInt()) {
                grammar.println("Необходимо написать цифру");
                grammar.nextLine();
                continue;
            }
            choosePersonLetter = grammar.nextInt();
            grammar.nextLine();
            if (choosePersonLetter == 0) {
                back = 0;
                stopped = false;
                break;
            }
            if (choosePersonLetter > wordToGuess.length() || choosePersonLetter < 1) {
                grammar.println("Ваш выбор выходит за диапазон слова. Пожалуйста попробуйте еще раз.");

                continue;
            }
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (guessedLetters[i] == choosePersonLetter) {
                    grammar.println("Данная буква уже отгадана, выберете другую букву");
                    retern = false;
                    break;
                }
            }
            if (!retern) {
                continue;
            }
            char randomChar = wordToGuess.charAt(choosePersonLetter - 1);
            List<QuestionEntity> quesans = questionRepository.getQuestionAnswerByLetter(String.valueOf(randomChar));
            String[] questions = new String[quesans.size()];
            for (int i = 0; i < quesans.size(); i++) {
                questions[i] = quesans.get(i).getQuestion();
            }
            String question = questions[random.nextInt(questions.length)];
            while (stopped) {
                grammar.println(question);
                grammar.println("Введите первую букву ответа или нажми 0, чтобы вернуться назад");
                String answer = grammar.nextLine();
                if ("0".equals(answer)) {
                    break;
                }
                if (answer.toUpperCase().charAt(0) == randomChar) {
                    grammar.println("Верно");
                    stopped = false;
                } else {
                    grammar.println("Введена не верная буква.\nВаш счет изменился: "
                            + scoreService.commitAnswerMismatch() + " очков.\nПопробуте еще раз");
                }
            }
        }
    }

    private void isGameFinished() {
        arrayRandomWord[choosePersonLetter - 1] = wordToGuess.charAt(choosePersonLetter - 1);
        back = 0;
        stopped = true;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if ('*' == arrayRandomWord[i]) {
                back = 1;
                stopped = false;
                break;
            }
        }
        guessedLetters[choosePersonLetter - 1] = choosePersonLetter;
        userGameStateRepository.writeProgress(GlobalVariable.getCurrentUser().getLogin(), wordToGuess, arrayRandomWord);
    }
}
