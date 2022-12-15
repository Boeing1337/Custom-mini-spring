package tech.wg.dao;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@Component
public class UserRepository {
    @InjectProperty
    private static String baseDirectory;
    @InjectProperty
    private static String userPassPath;
    @InjectProperty
    private static String userProgressPath;

    private String getBaseDirectory() {
        return "." + separator + baseDirectory + separator;
    }

    public boolean createUser(String login, String pass) {
        PrintWriter writer = null;
        try {
            Files.createDirectory(Path.of(getBaseDirectory() + login));
            Files.createFile(Path.of(getBaseDirectory() + login + separator + userPassPath));
            Files.createFile(Path.of(getBaseDirectory() + login + separator + userProgressPath));
            writer = new PrintWriter(getBaseDirectory() + login + separator + userPassPath, UTF_8);
            writer.println(pass);
            return true;
        } catch (IOException e) {
            log.warn(e);
            try {
                Files.deleteIfExists(Path.of(getBaseDirectory() + login + separator + userProgressPath));
                Files.deleteIfExists(Path.of(getBaseDirectory() + login + separator + userPassPath));
                Files.deleteIfExists(Path.of(getBaseDirectory() + login));
            } catch (IOException ex) {
                log.warn(ex);
            }
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
        return false;
    }

    public boolean isUserPresents(String login) {
        try {
            Path path = Path.of(getBaseDirectory() + login);
            Files.createDirectory(path);
            Files.delete(path);
            return false;
        } catch (FileAlreadyExistsException e) {
            log.warn(e);
            return true;
        } catch (IOException e) {
            log.warn(e);
            return false;
        }
    }

    public Optional<UserEntity> getUser(String login) {
        File file = new File(getBaseDirectory() + login + separator + userPassPath);
        try (Scanner scanner = new Scanner(file, UTF_8)) {
            if (scanner.hasNextLine()) {
                return Optional.of(new UserEntity(login, scanner.nextLine()));
            }
        } catch (Exception e) {
            log.warn(e);
        }
        return Optional.empty();
    }
}
