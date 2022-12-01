package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.annotations.InjectProperty;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserRepository;

import java.util.Optional;
import java.util.Scanner;

@Component
public class LoginService {

    @InjectObject
    private UserRepository userRepository;

    @InjectProperty(value = "path.to.score")
    private String path;
    private Scanner scanner = new Scanner(System.in);

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    public void authorization() {
        Optional<UserEntity> opt;
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
