package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static dao.Constants.BASE_DIRECTORY;
import static java.io.File.separator;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void testCreateUserCheckIfFolderExists() {
        UserRepository test1 = new UserRepository();
        boolean resolt = test1.createUser("poxuy");
        Assertions.assertTrue(resolt," otvet");
        File file = new File("."+ separator+ BASE_DIRECTORY+separator+"poxuy");
        Assertions.assertTrue(file.exists(),"ehfjf");;
deleted();
    }
    @Test
    void testCreateUserCheckIfFolder() {
        UserRepository test1 = new UserRepository();
        boolean resolt = test1.createUser("poxuy");
        Assertions.assertTrue(resolt," otvet");
        File file = new File("."+ separator+ BASE_DIRECTORY+separator+"poxuy");
        Assertions.assertTrue(file.isDirectory(),"ehfjf");

    }


    @Test
    void testCreatedFilePassCheckPass() {
        UserRepository test2 = new UserRepository();
        boolean resolt = test2.createdFilePass("poxuy", "asdfgh");
        Assertions.assertTrue(resolt);
//        File file = new File("."+ separator+ BASE_DIRECTORY+separator+"poxuy"+separator+"pass");
deleted();

    }

    @Test
    void CheckUsersCreated() throws IOException {
        UserRepository test3 = new UserRepository();
        boolean resolt = test3.scanUser("poxuy");
        Assertions.assertFalse(resolt);

    }

    static void deleted() {
        try {
            UserRepository test1 = new UserRepository();
            Path path1 = Path.of("."+ separator+ BASE_DIRECTORY+separator+"poxuy"+separator+"pass");
            Path path = Path.of("."+ separator+ BASE_DIRECTORY+separator+"poxuy");
            Files.delete(path1);
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}