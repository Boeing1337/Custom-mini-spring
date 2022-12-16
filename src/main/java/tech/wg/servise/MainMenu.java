package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.tools.Grammar;

@Component
public class MainMenu {
    @InjectObject
    private Grammar grammar;
    @InjectObject
    private RegistrationUsers registrationUsers;
    @InjectObject
    private LoginService loginService;
    @InjectObject
    private AdminService adminService;

    public void startMainMenu() {
        while (true) {
            grammar.println("Введите 1 для входа в аккаунт");
            grammar.println("Введите 2 для регистрации аккаунта");
            grammar.println("Введите 0 для выхода из игры");

            switch (grammar.nextLine()) {
                case "1":
                    loginService.authorization();
                    break;
                case "2":
                    registrationUsers.registrationUser();
                    break;
                case "/admin":
                    adminService.action();
                    break;
                case "0":
                    return;
                default:
                    grammar.println("Нет такой команды. Попробуйте ещё раз");
            }
        }
    }
}
