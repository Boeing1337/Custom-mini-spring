package tech.wg.dao;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@Component
public class UserGameStateRepository {
    @InjectProperty
    private static String baseDirectory;
    @InjectProperty
    private static String userProgressPath;

    public String getProgress(String login) {
        File file = new File("." + separator + baseDirectory + separator + login + separator + userProgressPath);
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
        try (PrintWriter writer = new PrintWriter("." + separator + baseDirectory + separator + login +
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
        try (PrintWriter writer = new PrintWriter("." + separator + baseDirectory + separator + login +
                separator + "progress", UTF_8)) {
            writer.println("");
        } catch (Exception e) {
            log.warn(e);
        }
    }

}
