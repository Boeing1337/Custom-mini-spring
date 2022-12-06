package tech.wg.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.io.File.separator;
import static tech.wg.servise.Constants.BASE_DIRECTORY;

class UserRepositoryTest {
    private static final String PASS = "pass";
    private static final String PROGRESS = "progress";
    private static final String USER_LOGIN = "ArtiWell";
    private final UserRepository repository = new UserRepository();

    @BeforeEach
    void prepare() {
        repository.createUser(USER_LOGIN, PASS);
    }

    @Test
    void testIfFolderExists() {
        File file = new File("." + separator + BASE_DIRECTORY + separator + USER_LOGIN);
        Assertions.assertTrue(file.exists(), "dima_soskuchilsuy_po_cody");
    }

    @Test
    void testIfFolderTypeCreated() {
        File file = new File("." + separator + BASE_DIRECTORY + separator + USER_LOGIN);
        Assertions.assertTrue(file.isDirectory(), "dima_soskuchilsuy_po_cody");
    }

    @Test
    void testIfPassSaved() {

        UserEntity result = repository.getUser(USER_LOGIN).orElseThrow();
        Assertions.assertEquals(PASS, result.getPass());
    }


    @Test
    void testCheckIfUserPresents() {
        boolean result = repository.isUserPresents(USER_LOGIN);
        Assertions.assertTrue(result);
    }

    @AfterEach
    void deleted() {
        try {
            Path file = Path.of("." + separator + BASE_DIRECTORY + separator + USER_LOGIN + separator + PASS);
            Path file2 = Path.of("." + separator + BASE_DIRECTORY + separator + USER_LOGIN + separator + PROGRESS);
            Path directory = Path.of("." + separator + BASE_DIRECTORY + separator + USER_LOGIN);
            Files.deleteIfExists(file);
            Files.deleteIfExists(file2);
            Files.deleteIfExists(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}