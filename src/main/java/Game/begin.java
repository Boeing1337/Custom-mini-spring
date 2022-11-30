package Game;

import context.GlobalVariable;
import dao.KeywordsRepository;
import dao.QuestionRepository;
import dao.UserEntity;
import dao.UserGameStateRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class begin {
    private final Random random = new Random();
    private final KeywordsRepository words;
    private final QuestionRepository base;
    private final UserGameStateRepository userGameStateRepository;

    //    private final List<List<String>> ans = base.getAnswers();
    private Scanner scanner;
    private String randomWord;
    private char[] arrayRandomWord;
    private int[] guessedLetters;
    private boolean stopped = true;
    private int choosePersonLetter;
    private int back = 1;
    private int lengthThisWord;

    public begin(KeywordsRepository words, QuestionRepository base, UserGameStateRepository userGameStateRepository) {
        this.words = words;
        this.base = base;
        this.userGameStateRepository = userGameStateRepository;

    }


    public static void main(String[] args) {
        String login = "Art";
        GlobalVariable.setCurrentUser(new UserEntity(login, "123"));
        begin a = new begin(new KeywordsRepository(), new QuestionRepository(), new UserGameStateRepository());
        a.theGameContinue();

    }

    public void theGameContinue() {
        scanner = new Scanner(System.in);
        String progress = userGameStateRepository.getProgress(GlobalVariable.getCurrentUser().getLogin());
        if ("".equals(progress)) {
            System.out.println("Нет игры, кторую можно продолжить. Начни новую игру");
        }
        String[] a = progress.trim().split(";");
        randomWord = a[0];
        lengthThisWord = randomWord.length();
        arrayRandomWord = new char[lengthThisWord];
        guessedLetters = new int[lengthThisWord];
        for (int i = 0; i < lengthThisWord; i++) {
            arrayRandomWord[i] = a[1].charAt(i);
            if (arrayRandomWord[i] != '*') {
                guessedLetters[i] = i + 1;
            }
        }
        System.out.println(Arrays.toString(arrayRandomWord));
        while (back == 1) {
            stopped = true;
            startGame();
            if (back == 1) {
                disclosure();
            }
        }
        System.out.println("Поздравляю, вы набрали 100 очков");
    }

    public void theGameNew() {
        scanner = new Scanner(System.in);
        gameBegin();
        guessedLetters = new int[lengthThisWord];
        while (back == 1) {
            stopped = true;
            startGame();
            if (back == 1) {
                disclosure();
            }
        }
        System.out.println("Поздравляю, вы набрали 100 очков");
    }

    private String chooseCharter() {
        List<String> myWords = words.readKeywords();
        int randomIndexWord = random.nextInt(myWords.size());
        randomWord = myWords.get(randomIndexWord);
        return randomWord;
    }

    private void gameBegin() {
        lengthThisWord = chooseCharter().length();
        arrayRandomWord = new char[lengthThisWord];
        Arrays.fill(arrayRandomWord, 0, lengthThisWord, '*');
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
                break;
            }
            for (int i = 0; i < lengthThisWord; i++) {
                if (guessedLetters[i] == choosePersonLetter) {
                    System.out.println("Данная буква уже отгадана, выберете другую букву");
                    retern = false;
                    break;
                }
            }
            if (!retern) {
                continue;
            }
            char a = randomWord.charAt(choosePersonLetter - 1);
            List<String> quesans = base.getQuestionByLetter(String.valueOf(a));
            String[] question = new String[quesans.size() / 2];
            for (int i = 1, j = 0; i < quesans.size(); i += 2, j++) {
                question[j] = quesans.get(i);
            }
            String broughtOut = question[random.nextInt(question.length)];
            System.out.println(broughtOut);
            while (stopped) {
                System.out.println("Введите первую букву ответа в выбранное поле или нажми 0, чтобы вернуться назад");
                String answer = scanner.nextLine();
                if ("0".equals(answer)) {
                    break;
                }
                if (answer.toUpperCase().charAt(0) == a) {
                    System.out.println("Верно");
                    stopped = false;
                } else {
                    System.out.println("Введена не верная буква, попробуте еще раз");
                }
            }
        }
    }

    private void disclosure() {
        arrayRandomWord[choosePersonLetter - 1] = randomWord.charAt(choosePersonLetter - 1);
        System.out.println(Arrays.toString(arrayRandomWord));
        back = 0;
        for (int i = 0; i < lengthThisWord; i++) {
            if ('*' == arrayRandomWord[i]) {
                back = 1;
                break;
            }
        }
        guessedLetters[choosePersonLetter - 1] = choosePersonLetter;
        userGameStateRepository.writeProgress(GlobalVariable.getCurrentUser().getLogin(), randomWord, arrayRandomWord);
    }
}
