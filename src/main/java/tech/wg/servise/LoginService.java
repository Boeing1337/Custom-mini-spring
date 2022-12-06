package tech.wg.servise;

import tech.wg.context.GlobalVariable;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserRepository;
import tech.wg.tools.Grammar;

import java.util.Optional;

public class LoginService {

    private final UserRepository userRepository;
    private final Grammar grammar;

    public LoginService(UserRepository userRepository, Grammar grammar) {
        this.userRepository = userRepository;
        this.grammar = grammar;
    }

    public void authorization() {

        String login;
        while (true) {
            grammar.print("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню");
            login = grammar.readLine();
            if ("0".equals(login)) {
                return;
            }
            Optional<UserEntity> user = userRepository.getUser(login);
            if ((user.isEmpty())) {
                grammar.print("Такого логина не существует. Пожалуйста, попробуйте еще раз");
                continue;
            }
            while (true) {
                grammar.print("Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню");
                String pass = grammar.readLine();
                if ("0".equals(pass)) {
                    break;
                }
                if (pass.equals(user.get().getPass())) {
                    grammar.print("Добро пожаловать " + login + "!");
                    GlobalVariable.setCurrentUser(new UserEntity(login, pass));
                    return;
                }
                grammar.print("Не верный пароль! Попробуйте ещё раз.");
            }
        }
    }
}
