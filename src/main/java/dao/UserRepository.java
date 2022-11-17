package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static dao.Constants.BASE_DIRECTORY;

public class UserRepository {



    public boolean createUser(String login) {
        try {
            Files.createDirectory(Path.of("."+File.separator+ BASE_DIRECTORY+File.separator + login));
            Files.createFile(Path.of("."+File.separator+BASE_DIRECTORY+File.separator + login + File.separator+"pass"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createdFilePass(String login, String pass) {
        try (PrintWriter writer = new PrintWriter("."+File.separator+BASE_DIRECTORY+File.separator + login +
                File.separator+"pass")) {
            writer.println(pass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean scanUser (String login) throws IOException {
        try {
            Files.createDirectory(Path.of("."+File.separator+BASE_DIRECTORY+File.separator + login));
            return false;
        } catch (FileAlreadyExistsException e) {
            return true;
        }
    }
    public Optional<UserEntity> information (String login, String scan){
        File file = new File("."+File.separator+BASE_DIRECTORY+File.separator + login+File.separator+ scan);
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            return Optional.of(new UserEntity(login,scanner.nextLine()));
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
        return Optional.empty();
    }

}
