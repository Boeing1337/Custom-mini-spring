package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static dao.Constants.BASE_DIRECTORY;
import static java.io.File.separator;

class UserRepositoryTest {
    static final String PASS = "pass";
    static String USER_LOGIN = "ArtiWell";
    UserRepository repository = new UserRepository();

    @BeforeEach
    void prepare() {
        repository.createUser(USER_LOGIN);
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
        boolean isPassCreated = repository.createPass(USER_LOGIN, PASS);
        Assertions.assertTrue(isPassCreated);
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
            Path path1 = Path.of("." + separator + BASE_DIRECTORY + separator + USER_LOGIN + separator + PASS);
            Path path = Path.of("." + separator + BASE_DIRECTORY + separator + USER_LOGIN);
            Files.delete(path1);
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}