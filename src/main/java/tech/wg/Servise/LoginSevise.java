package tech.wg.Servise;

import tech.wg.context.GlobalVariable;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public class LoginSevise {
    private final UserRepository userRepository;
    private Optional<UserEntity> opt;

    private Scanner scanner = new Scanner(System.in);

    public LoginSevise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    public void authorization() {
        boolean allows = true;
        String login;
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
                    GlobalVariable.setCurrentUser(new UserEntity(login, pass));
                    allows = false;
                }
            }
        }
    }
}