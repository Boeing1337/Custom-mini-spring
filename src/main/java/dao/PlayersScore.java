package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PlayersScore {
    private final Set<String> cash = new LinkedHashSet<>();

    private ScoreEntity newScore;

    String login = newScore.getLogin();
    int wins = newScore.getWin();
    int loss = newScore.getLoss();
    double winRate = newScore.getWinRate();

    public PlayersScore(ScoreEntity newScore) {
        this.newScore = newScore;
        readePlayerScore();
    }


    public List<String> addPlayersScore(ScoreEntity newScore) throws FileNotFoundException {
        File file = new File("PlayersScore");
        Scanner scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
             String cash = scanner.nextLine();
             if (cash.contentEquals(login)) {

             }
         }
        cash.addAll(newScore);
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            writer.write(String.join("\n", cash));
        } catch (Exception e) {
            System.out.println("не получилось добавить счёт");
        }
        cash.clear();
        return readePlayerScore();

    }


    public List<String> EditPlayerScore() {
        File file = new File("PlayersScore");
        readePlayerScore();
        cash.remove(oldScore);
        cash.add(newScore);
        String perenosStroki = "\n";
        StringBuilder sb = new StringBuilder();
        for (String element : cash) {
            sb.append(element);
            sb.append(perenosStroki);
        }
        String res = sb.toString();
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            writer.write(res);
        } catch (Exception e) {
            System.out.println("не удалось изменить счёт");
        }
        cash.clear();
        return readePlayerScore();
    }

    public List<String> readePlayerScore() {
        if (cash.isEmpty()) {
            File file = new File("PlayersScore");
            try (Scanner scanner = new Scanner(file, UTF_8)) {
                while (scanner.hasNextLine()) {
                    cash.add(scanner.nextLine());
                }
            } catch (Exception e) {
                cash.clear();
                System.out.println("нет файла с очками");
            }
        }
        return List.copyOf(cash);
    }

    public void deletePlayerScore(String wrongWord) {
        File file = new File("PlayersScore");
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            cash.remove(wrongWord);
            writer.write(String.join("\n", cash));
        } catch (Exception e) {
            System.out.println("не удалось удалить счёт");
        }
    }
}
