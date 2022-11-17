package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
        try (PrintWriter writer = new PrintWriter("."+File.separator+BASE_DIRECTORY+File.separator + login + File.separator+"pass")) {
            writer.println(pass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean scanUser (String login) throws IOException {
        try {
            Files.createDirectory(Path.of(".\\Users\\" + login));
            return false;
        } catch (FileAlreadyExistsException e) {
            return true;
        }
    }

    public static List information (String login){
        List<String> abc = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String scan = scanner.nextLine();
        File file = new File(".\\Users\\" + login +"\\"+ scan);
        try (Scanner scanner1 = new Scanner(file)){
            while (scanner1.hasNext()){
                abc.add(scanner1.nextLine());
            }
            return abc;
        }catch (Exception e){
            return null;
        }
    }

}
