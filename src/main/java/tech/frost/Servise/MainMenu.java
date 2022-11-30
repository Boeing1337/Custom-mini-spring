package tech.frost.Servise;

import tech.frost.Registration.RegistrationUsers;

import java.util.Scanner;

public class MainMenu {

    private final RegistrationUsers registrationUsers;
    private final LoginSevise loginSevise;
    private Scanner scanner = new Scanner(System.in);

    public MainMenu(LoginSevise loginSevise, RegistrationUsers registrationUsers) {
          this.loginSevise = loginSevise;
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
                    loginSevise.authorization();
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
