import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class WordApplication {
    public static boolean stoped = true;


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (stoped) {
            System.out.println("Введите логин");
            String login = scanner.nextLine();
            if (createUser(login)) {
                createUser(login);
                stoped = false;
            } else {
                System.out.println("Пользователь с таким логином уже существует или логин введен некорректно. " +
                        "Пожалуйста, попробуйте еще раз");
                continue;
            }

            while (!stoped) {
                System.out.println("Введите пароль");
                String pass = scanner.nextLine();
                if (createdFilePass(login, pass) && !pass.equals("")) {
                    createdFilePass(login, pass);

                    System.out.println("Введите пароль повторно");
                    String passRe = scanner.nextLine();
                    if (passRe.equals(pass)) {
                        stoped = true;
                    } else {
                        System.out.println("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
                    }
                } else {
                    System.out.println("Пароль не соответствует требованиям");
                }
            }
            stoped = false;
        }


    }

    public static boolean createUser(String login) {
        try {
            Files.createDirectory(Path.of(".\\Users\\" + login));
            Files.createFile(Path.of(".\\Users\\" + login + "\\pass"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createdFilePass(String login, String pass) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(".\\Users\\" + login + "\\pass")) {
            writer.println(pass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
