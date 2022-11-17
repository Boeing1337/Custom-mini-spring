package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static dao.Constants.BASE_DIRECTORY;
import static java.io.File.separator;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
static String user = "ArtiWell";
    @Test
    void testCreateUserCheckIfFolderExists() {
        UserRepository test1 = new UserRepository();
        boolean resolt = test1.createUser(user);
        Assertions.assertTrue(resolt," otvet");
        File file = new File("."+ separator+ BASE_DIRECTORY+separator+user);
        Assertions.assertTrue(file.exists(),"ehfjf");;
deleted();
    }
    @Test
    void testCreateUserCheckIfFolder() {
        UserRepository test1 = new UserRepository();
        boolean resolt = test1.createUser(user);
        Assertions.assertTrue(resolt," otvet");
        File file = new File("."+ separator+ BASE_DIRECTORY+separator+user);
        Assertions.assertTrue(file.isDirectory(),"ehfjf");
deleted();
    }


    @Test
    void testCreatedFilePassCheckPass() {
        UserRepository test2 = new UserRepository();
        test2.createUser(user);
        boolean resolt = test2.createdFilePass(user, "123456qwe");
        Assertions.assertTrue(resolt);
//        File file = new File("."+ separator+ BASE_DIRECTORY+separator+user+separator+"pass");
deleted();

    }

    @Test
    void testCheckIsUserPresents() throws IOException {
        UserRepository test3 = new UserRepository();
        test3.createUser(user);
        boolean resolt = test3.isUserPresents(user);
        Assertions.assertTrue(resolt);
deleted();
    }

    @Test
    void testCheckInformation(){
        UserRepository test4 = new UserRepository();
        test4.createUser(user);
        test4.createdFilePass(user, "pass");
        Optional<UserEntity> resolt = test4.information(user, "pass");
        if (resolt.isPresent()){
            System.out.println(resolt.get().getPass());
        }
deleted();
    }

    static void deleted() {
        try {
            UserRepository test1 = new UserRepository();
            Path path1 = Path.of("."+ separator+ BASE_DIRECTORY+separator+user+separator+"pass");
            Path path = Path.of("."+ separator+ BASE_DIRECTORY+separator+user);
            Files.delete(path1);
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}