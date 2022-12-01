package tech.wg.Servise;

import tech.wg.context.GlobalVariable;
import tech.wg.dao.KeywordsRepository;
import tech.wg.dao.QuestionRepository;
import tech.wg.dao.UserGameStateRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BeginingTheGame {
    private final Random random = new Random();
    private final KeywordsRepository wordsRepository;
    private final QuestionRepository questionRepository;
    private final UserGameStateRepository userGameStateRepository;
    private Scanner scanner;
    private String wordToGuess;
    private char[] arrayRandomWord;
    private int[] guessedLetters;
    private boolean stopped = true;
    private int choosePersonLetter;
    private int back = 1;

    public BeginingTheGame(KeywordsRepository wordsRepository, QuestionRepository questionRepository,
                           UserGameStateRepository userGameStateRepository) {
        this.wordsRepository = wordsRepository;
        this.questionRepository = questionRepository;
        this.userGameStateRepository = userGameStateRepository;

    }

    public void theGameContinue() {
        scanner = new Scanner(System.in);
        String progress = userGameStateRepository.getProgress(GlobalVariable.getCurrentUser().getLogin());
        if ("".equals(progress)) {
            System.out.println("Нет игры, кторую можно продолжить. Начни новую игру");
            return;
        }
        String[] a = progress.trim().split(";");
        wordToGuess = a[0];
        arrayRandomWord = new char[wordToGuess.length()];
        guessedLetters = new int[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) {
            arrayRandomWord[i] = a[1].charAt(i);
            if (arrayRandomWord[i] != '*') {
                guessedLetters[i] = i + 1;
            }
        }
        System.out.println(Arrays.toString(arrayRandomWord));
        continuous();
    }

    public void theGameNew() {
        scanner = new Scanner(System.in);
        gameBegin();
        guessedLetters = new int[wordToGuess.length()];
        continuous();
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
            System.out.println("Поздравляю, вы набрали 100 очков");
        }
    }

    private String chooseWordToGuess() {
        List<String> myWords = wordsRepository.readKeywords();
        int randomIndexWord = random.nextInt(myWords.size());
        wordToGuess = myWords.get(randomIndexWord);
        return wordToGuess;
    }

    private void gameBegin() {
        arrayRandomWord = new char[chooseWordToGuess().length()];
        Arrays.fill(arrayRandomWord, 0, wordToGuess.length(), '*');
        System.out.println(Arrays.toString(arrayRandomWord));
    }

    private void startGame() {
        while (stopped) {
            boolean retern = true;
            System.out.println("Выбери номер буквы или нажми 0, чтобы вернуться назад");
            if (!scanner.hasNextInt()) {
                System.out.println("Необходимо написать цифру");
                scanner.nextLine();
                continue;
            }
            choosePersonLetter = scanner.nextInt();
            scanner.nextLine();
            if (choosePersonLetter == 0) {
                back = 0;
                stopped = false;
                break;
            }
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (guessedLetters[i] == choosePersonLetter) {
                    System.out.println("Данная буква уже отгадана, выберете другую букву");
                    retern = false;
                    break;
                }
            }
            if (!retern) {
                continue;
            }
            char randomChar = wordToGuess.charAt(choosePersonLetter - 1);
            List<String> quesans = questionRepository.getQuestionAnswerByLetter(String.valueOf(randomChar));
            String[] questions = new String[quesans.size() / 2];
            for (int i = 1, j = 0; i < quesans.size(); i += 2, j++) {
                questions[j] = quesans.get(i);
            }
            String question = questions[random.nextInt(questions.length)];
            System.out.println(question);
            while (stopped) {
                System.out.println("Введите первую букву ответа или нажми 0, чтобы вернуться назад");
                String answer = scanner.nextLine();
                if ("0".equals(answer)) {
                    break;
                }
                if (answer.toUpperCase().charAt(0) == randomChar) {
                    System.out.println("Верно");
                    stopped = false;
                } else {
                    System.out.println("Введена не верная буква, попробуте еще раз");
                }
            }
        }
    }

    private void isGameFinished() {
        arrayRandomWord[choosePersonLetter - 1] = wordToGuess.charAt(choosePersonLetter - 1);
        System.out.println(Arrays.toString(arrayRandomWord));
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
