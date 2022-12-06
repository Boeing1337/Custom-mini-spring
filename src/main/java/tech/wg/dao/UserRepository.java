package tech.wg.dao;

import lombok.extern.log4j.Log4j2;

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
import static tech.wg.servise.Constants.BASE_DIRECTORY;

@Log4j2
public class UserRepository {


    public boolean createUser(String login) {
        try {
            Files.createDirectory(Path.of("." + separator + BASE_DIRECTORY + separator + login));
            Files.createFile(Path.of("." + separator + BASE_DIRECTORY + separator + login + separator + "pass"));
            Files.createFile(Path.of("." + separator + BASE_DIRECTORY + separator + login + separator + "progress"));
            return true;
        } catch (Exception e) {
            log.warn(e);
            return false;
        }
    }

    public boolean createPass(String login, String pass) {
        try (PrintWriter writer = new PrintWriter("." + separator + BASE_DIRECTORY + separator + login +
                separator + "pass", UTF_8)) {
            writer.println(pass);
            return true;
        } catch (Exception e) {
            log.warn(e);
            return false;
        }
    }

    public void deleteUser(String login) {
        try {
            Files.delete(Path.of("." + separator + BASE_DIRECTORY + separator + login + separator + "progress"));
            Files.delete(Path.of("." + separator + BASE_DIRECTORY + separator + login + separator + "pass"));
            Files.delete(Path.of("." + separator + BASE_DIRECTORY + separator + login));
        } catch (Exception e) {
            log.warn(e);
        }
    }


    public boolean isUserPresents(String login) {
        try {
            Path path = Path.of("." + separator + BASE_DIRECTORY + separator + login);
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
        File file = new File("." + separator + BASE_DIRECTORY + separator + login + separator + "pass");
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
