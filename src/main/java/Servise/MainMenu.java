package Servise;

import Registration.RegistrationUsers;

import java.util.Scanner;

public class MainMenu {

    private RegistrationUsers registrationUsers;
    private LoginSevise loginSevise;
    private Scanner scanner = new Scanner(System.in);

    public MainMenu(LoginSevise loginSevise, RegistrationUsers registrationUsers) {
          this.loginSevise = loginSevise;
          this.registrationUsers = registrationUsers;

    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    public void startMainMenu() {
        String input;
        initScanner();
        System.out.println("Введите 1 для входа в аккаунт");
        System.out.println("Введите 2 для регистрации аккаунта");
        System.out.println("Введите 0 для выхода из игры");
        input = scanner.nextLine();
        if (input.equals("1")) {
            loginSevise.authorization();
        }
        if (input.equals("2")) {
            registrationUsers.registrationUser();
        }
    }
}
