package tech.wg.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.extention.ExtendWIthTech;
import tech.ioc.annotations.InjectProperty;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.io.File.separator;

@ExtendWIthTech
class UserRepositoryTest {

    private static final String userPass = "pass";
    @InjectProperty
    private static String baseDirectory;
    @InjectProperty
    private static String userProgressPath;
    @InjectProperty
    private static String userPassPath;
    private static final String USER_LOGIN = "ArtiWell";
    private final UserRepository repository = new UserRepository();

    @BeforeEach
    void prepare() {
        repository.createUser(USER_LOGIN, userPass);
    }

    @Test
    void testIfFolderExists() {
        File file = new File("." + separator + baseDirectory);
        Assertions.assertTrue(file.isDirectory(), "Не создана директория для пользователей");
    }

    @Test
    void testIfPassSaved() {
        UserEntity result = repository.getUser(USER_LOGIN).orElseThrow();
        Assertions.assertEquals(userPass, result.getPass());
    }


    @Test
    void testCheckIfUserPresents() {
        boolean result = repository.isUserPresents(USER_LOGIN);
        Assertions.assertTrue(result);
    }

    @AfterEach
    void deleted() {
        try {
            Path file = Path.of("." + separator + baseDirectory + separator + USER_LOGIN + separator + userPassPath);
            Path file2 = Path.of("." + separator + baseDirectory + separator + USER_LOGIN + separator + userProgressPath);
            Path directory = Path.of("." + separator + baseDirectory + separator + USER_LOGIN);
            Files.deleteIfExists(file);
            Files.deleteIfExists(file2);
            Files.deleteIfExists(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}