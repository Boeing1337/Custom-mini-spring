package tech.wg.dao;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.wg.servise.Constants.BASE_DIRECTORY;

@Log4j2
public class UserGameStateRepository {

    public String getProgress(String login) {
        File file = new File("." + separator + BASE_DIRECTORY + separator + login + separator + "progress");
        try (Scanner scanner = new Scanner(file, UTF_8)) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }
        } catch (Exception e) {
            log.warn(e);
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
            log.warn(e);
            return false;
        }
    }

    public void deleteProgress(String login) {
        try (PrintWriter writer = new PrintWriter("." + separator + BASE_DIRECTORY + separator + login +
                separator + "progress", UTF_8)) {
            writer.println("");
        } catch (Exception e) {
            log.warn(e);
        }
    }

}
