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
            grammar.write("Введите 1 для входа в аккаунт");
            grammar.write("Введите 2 для регистрации аккаунта");
            grammar.write("Введите 0 для выхода из игры");

            switch (grammar.readLine()) {
                case "1":
                    loginService.authorization();
                    return;
                case "2":
                    registrationUsers.registrationUser();
                    return;
                case "/admin":
                    adminService.action();
                    break;
                case "0":
                    return;
                default:
                    grammar.write("Нет такой команды. Попробуйте ещё раз");
            }
        }
    }
}
