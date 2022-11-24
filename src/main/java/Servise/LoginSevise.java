package Servise;

import dao.UserEntity;
import dao.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public class LoginSevise {
    private final UserRepository userRepository;
    Optional<UserEntity> opt;
    boolean allows = true;
    String login;
    Scanner scanner = new Scanner(System.in);

    public LoginSevise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    public void authorization() {
        initScanner();
        while (allows) {
            System.out.println("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню");
            login = scanner.nextLine();
            if ("0".equals(login)) {
                return;
            }
            opt = userRepository.getUser(login);
            if ((opt.isEmpty())) {
                System.out.println("Такого логина не существует. Пожалуйста, попробуйте еще раз");
                continue;
            }
            while (allows) {
                System.out.println("Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню");
                String pass = scanner.nextLine();
                if ("0".equals(pass)) {
                    break;
                }
                if (!(pass.equals(opt.get().getPass()))) {
                    System.out.println("Не верный пароль! Попробуйте ещё раз.");
                    continue;
                }
                if (pass.equals(opt.get().getPass())) {
                    System.out.println("Добро пожаловать " + login + "!");
                    allows = false;
                }
            }
        }
    }

//    public boolean enterYourLogin() {
//        System.out.println("Что бы вернуться назад нажмите 0");
//        System.out.println("Введите ваш логин:");
//        while (!scanner.hasNextLine()) {
//        }
//        login = scanner.nextLine();
//        if (login.equals("0")) {
//            return false;
//        }
//        opt = userRepository.getUser(login);
//        if (opt.isEmpty()) {
//            System.out.println("Такого логина не существует");
//            enterYourLogin();
//        } else {
//            return true;
//        }
//        return false;
//    }



//    public boolean enterYourPassword() {
//        System.out.println("Что бы вернуться назад нажмите 0");
//        System.out.println("Введите пароль:");
//        String pass = scanner.nextLine();
//        if (pass.equals("0")) {
//            return false;
//        }
//
//        if (!(pass.equals(opt.get().getPass()))) {
//            System.out.println("Не верный пароль!");
//            enterYourPassword();
//        } else {
//            System.out.println("Добро пожаловать " + login + "!");
//            return true;
//        }
//        return false;
//    }
}
