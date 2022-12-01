package tech.wg.dao;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.wg.dao.Constants.BASE_DIRECTORY;

public class UserRepository {


    public boolean createUser(String login) {
        try {
            Files.createDirectory(Path.of("." + separator + BASE_DIRECTORY + separator + login));
            Files.createFile(Path.of("." + separator + BASE_DIRECTORY + separator + login + separator + "pass"));
            Files.createFile(Path.of("." + separator + BASE_DIRECTORY + separator + login + separator + "progress"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createPass(String login, String pass) {
        try (PrintWriter writer = new PrintWriter("." + separator + BASE_DIRECTORY + separator + login +
                separator + "pass", UTF_8)) {
            writer.println(pass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserPresents(String login) {
        try {
            Path path = Path.of("." + separator + BASE_DIRECTORY + separator + login);
            Files.createDirectory(path);
            Files.delete(path);
            return false;
        } catch (FileAlreadyExistsException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<UserEntity> getUser(String login) {
        File file = new File("." + separator + BASE_DIRECTORY + separator + login + separator + "pass");
        try (Scanner scanner = new Scanner(file, UTF_8)) {
            if (scanner.hasNextLine()) {
                return Optional.of(new UserEntity(login, scanner.nextLine()));
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return Optional.empty();
    }
}
