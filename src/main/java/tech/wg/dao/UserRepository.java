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
    private static final String BASE_PATH = "." + separator + BASE_DIRECTORY + separator;


//    public boolean createUser(String login) {
//        try {
//            Files.createDirectory(Path.of(BASE_PATH + login));
//            Files.createFile(Path.of(BASE_PATH + login + separator + "pass"));
//            Files.createFile(Path.of(BASE_PATH + login + separator + "progress"));
//            return true;
//        } catch (Exception e) {
//            log.warn(e);
//            return false;
//        }
//    }

    public boolean createUser(String login, String pass) {
        PrintWriter writer = null;
        try {
            Files.createDirectory(Path.of(BASE_PATH + login));
            Files.createFile(Path.of(BASE_PATH + login + separator + "pass"));
            Files.createFile(Path.of(BASE_PATH + login + separator + "progress"));
            writer = new PrintWriter(BASE_PATH + login + separator + "pass", UTF_8);
            writer.println(pass);
            return true;
        } catch (IOException e) {
            log.warn(e);
            try {
                Files.deleteIfExists(Path.of(BASE_PATH + login + separator + "progress"));
                Files.deleteIfExists(Path.of(BASE_PATH + login + separator + "pass"));
                Files.deleteIfExists(Path.of(BASE_PATH + login));
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


//    public void deleteUser(String login) {
//        try {
//            Files.deleteIfExists(Path.of(BASE_PATH + login + separator + "progress"));
//            Files.deleteIfExists(Path.of(BASE_PATH + login + separator + "pass"));
//            Files.deleteIfExists(Path.of(BASE_PATH + login));
//        } catch (Exception e) {
//            log.warn(e);
//        }
//    }


    public boolean isUserPresents(String login) {
        try {
            Path path = Path.of(BASE_PATH + login);
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
        File file = new File(BASE_PATH + login + separator + "pass");
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
