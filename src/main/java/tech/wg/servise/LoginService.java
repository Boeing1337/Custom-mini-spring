package tech.wg.servise;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.wg.context.GlobalVariable;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserRepository;
import tech.wg.tools.Grammar;

import java.util.Optional;

@Component
public class LoginService {
    @InjectObject
    private UserRepository userRepository;
    @InjectObject
    private Grammar grammar;
    @InjectObject
    private Encryption encryption;
    @InjectObject
    private GameMenu gameMenu;


    public void authorization() {

        String login;
        while (true) {
            grammar.write("Введите логин или 0, чтобы вернуться в предыдущее меню");
            login = grammar.readLine();
            if ("0".equals(login)) {
                return;
            }
            Optional<UserEntity> user = userRepository.getUser(login);
            if ((user.isEmpty())) {
                grammar.write("Такого логина не существует. Пожалуйста, попробуйте еще раз");
                continue;
            }
            while (true) {
                grammar.write("Введите пароль, или 0, чтобы вернуться в предыдущее меню");
                String pass = grammar.readLine();
                if ("0".equals(pass)) {
                    break;
                }
                pass = encryption.action(pass);
                if (pass.equals(user.get().getPass())) {
                    grammar.write("Добро пожаловать " + login + "!");
                    GlobalVariable.setCurrentUser(new UserEntity(login, pass));
                    gameMenu.startGameMenu();
                    return;
                }
                grammar.write("Не верный пароль! Попробуйте ещё раз.");
            }
        }
    }
}
