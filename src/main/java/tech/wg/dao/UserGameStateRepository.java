package tech.wg.dao;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.wg.dao.Constants.BASE_DIRECTORY;

public class UserGameStateRepository {

    public String getProgress(String login) {
        File file = new File("." + separator + BASE_DIRECTORY + separator + login + separator + "progress");
        try (Scanner scanner = new Scanner(file, UTF_8)) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }
        } catch (Exception ignore) {
            System.out.println("Чувак ты все нахуй сломал, идиот");
        }
        return "";
    }

    public boolean writeProgress(String login, String word, char[] progress) {
        try (PrintWriter writer = new PrintWriter("." + separator + BASE_DIRECTORY + separator + login +
                separator + "progress", UTF_8)) {
            String userProgress = new String(progress);
            writer.println(word + ";" + userProgress);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
