package Registration;

import dao.UserRepository;

import java.util.Scanner;

public class RegistrationUsers {
    private final UserRepository userRepository;
    Scanner scanner = new Scanner(System.in);

    private RegistrationUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    private boolean checkDoublePass(String pass1, String pass2) {
        return pass2.equals(pass1);
    }

    public void registrationUser() {
        initScanner();
        boolean allows = true;
        String login = null;
        String pass1 = null;
        String pass2 = null;
        while (allows) {
            System.out.println("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню");
            login = scanner.nextLine();
            if ("0".equals(login)) {
                return;
            }
            if (userRepository.isUserPresents(login)) {
                System.out.println("Такой логин уже занят. Пожалуйста, попробуйте еще раз");
                continue;
            }
            while (allows) {
                System.out.println("Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню");
                pass1 = scanner.nextLine();
                if ("0".equals(pass1)) {
                    break;
                }
                if ("".equals(pass1)) {
                    System.out.println("Пароль должен содержать значение");
                    continue;
                }
                while (allows) {
                    System.out.println("Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню");
                    pass2 = scanner.nextLine();
                    if ("0".equals(pass2)) {
                        break;
                    }
                    if (checkDoublePass(pass1, pass2)) {
                        allows = false;
                    } else {
                        System.out.println("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
                    }
                }
            }
            if (!allows) {
                createdFiles(login, pass1);
            }
        }
    }

    private void createdFiles(String login, String pass1) {
        userRepository.createUser(login);
        userRepository.createPass(login, pass1);
    }
}
