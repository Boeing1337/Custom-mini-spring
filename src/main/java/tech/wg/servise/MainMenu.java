package tech.wg.servise;

import tech.wg.tools.Grammar;

public class MainMenu {
    private final Grammar grammar;
    private final RegistrationUsers registrationUsers;
    private final LoginService loginService;

    public MainMenu(Grammar grammar, LoginService loginService, RegistrationUsers registrationUsers) {
        this.grammar = grammar;
        this.loginService = loginService;
        this.registrationUsers = registrationUsers;
    }

    public void startMainMenu() {
        boolean reran = true;
        String input;
        grammar.write("Введите 1 для входа в аккаунт");
        grammar.write("Введите 2 для регистрации аккаунта");
        grammar.write("Введите 0 для выхода из игры");
        while (reran) {
            input = grammar.readLine();
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
                    grammar.write("Нет такой команды. Попробуйте ещё раз");
                    break;
            }
        }
    }
}
