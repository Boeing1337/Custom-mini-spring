package tech.wg.servise;

import java.util.Scanner;

public class MainMenu {

    private final RegistrationUsers registrationUsers;
    private final LoginService loginService;
    private Scanner scanner = new Scanner(System.in);

    public MainMenu(LoginService loginService, RegistrationUsers registrationUsers) {
        this.loginService = loginService;
        this.registrationUsers = registrationUsers;
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    public void startMainMenu() {
        boolean reran = true;
        String input;
        initScanner();
        System.out.println("Введите 1 для входа в аккаунт");
        System.out.println("Введите 2 для регистрации аккаунта");
        System.out.println("Введите 0 для выхода из игры");
        while (reran) {
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    loginService.authorization();
                break;
                case "2":
                    registrationUsers.registrationUser();
                    break;
                case "0":
                    reran = false;
                    break;
                default:
                    System.out.println("Нет такой команды. Попробуйте ещё раз");
                    break;
            }
        }
    }
}
