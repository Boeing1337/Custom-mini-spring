package Game;

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
    private final KeywordsRepository words = new KeywordsRepository();
    private final QuestionRepository base = new QuestionRepository();
    private final UserGameStateRepository userGameStateRepository;
    private final UserEntity userEntity;
    //    private final List<List<String>> ans = base.getAnswers();
    private final List<List<String>> qes = base.getQuestions();
    private final String[] content = base.getReading();
    private Scanner scanner;
    private String randomWord;
    private char[] arrayRandomWord;
    private int[] guessedLetters;
    private boolean stopped = true;
    private int choosePersonLetter;
    private int back = 1;
    private int lengthThisWord;

    public begin(UserGameStateRepository userGameStateRepository, UserEntity userEntity) {
        this.userGameStateRepository = userGameStateRepository;
        this.userEntity = userEntity;
    }


//    public static void main(String[] args) {
//        begin a = new begin();
//        a.theGameNew();
//
//    }

    public void theGameContinue() {
        scanner = new Scanner(System.in);
        String progress = userGameStateRepository.getProgress(userEntity.getLogin());
        if ("".equals(progress)) {
            System.out.println("Нет игры, кторую можно продолжить. Начни новую игру");
        }
        String[] a = progress.trim().split(";");
        randomWord = a[0];
        lengthThisWord = randomWord.length();
        for (int i = 0; i < lengthThisWord; i++) {
            arrayRandomWord[i] = a[1].charAt(i);
            if (arrayRandomWord[i] == '*') {
                guessedLetters[i] = i + 1;
            }
        }
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
            for (int i = 0; i < content.length; i++) {
                if (content[i].charAt(0) != a) {
                    continue;
                }
                String broughtOut = qes.get(i).get(random.nextInt(qes.get(i).size()));
                System.out.println(broughtOut);
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
                    i = 0;
                    continue;
                }
                break;
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
        userGameStateRepository.writeProgress(userEntity.getLogin(), randomWord, arrayRandomWord);
    }
}
