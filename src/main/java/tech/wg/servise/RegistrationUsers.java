package tech.wg.servise;

import tech.wg.context.GlobalVariable;
import tech.wg.dao.PlayersScoreRepository;
import tech.wg.dao.ScoreEntity;
import tech.wg.dao.UserEntity;
import tech.wg.dao.UserRepository;
import tech.wg.tools.Grammar;

public class RegistrationUsers {
    private Grammar grammar;
    private UserRepository userRepository;
    private Encryption encryption;
    private PlayersScoreRepository playersScoreRepository;


    private boolean checkDoublePass(String pass1, String pass2) {
        return pass2.equals(pass1);
    }

    public void registrationUser() {
        boolean allows = true;
        String pass1 = null;
        while (allows) {
            grammar.write("Введите логин или нажмите 0, чтобы вернуться в предыдущее меню");
            String login = grammar.readLine();
            if ("0".equals(login)) {
                return;
            }
            if (userRepository.isUserPresents(login)) {
                grammar.write("Такой логин уже занят. Пожалуйста, попробуйте еще раз");
                continue;
            }
            while (allows) {
                grammar.write("Введите пароль, или нажмите 0, чтобы вернуться в предыдущее меню");
                pass1 = grammar.readLine();
                if ("0".equals(pass1)) {
                    break;
                }
                if ("".equals(pass1)) {
                    grammar.write("Пароль должен содержать значение");
                    continue;
                }
                while (allows) {
                    grammar.write("Введите пароль повторно, или нажмите 0, чтобы вернуться в предыдущее меню");
                    String pass2 = grammar.readLine();
                    if ("0".equals(pass2)) {
                        break;
                    }
                    if (checkDoublePass(pass1, pass2)) {
                        allows = false;
                    } else {
                        grammar.write("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
                    }
                }
            }
            if (!allows) {
                createdFiles(login, pass1);
                GlobalVariable.setCurrentUser(new UserEntity(login, pass1));
                playersScoreRepository.saveScore(new ScoreEntity(GlobalVariable.currentUser.getLogin(),
                        0, 0, 0.00, 0));
                grammar.write("Добро пожаловать " + login + "!");
            }
        }
    }

    private void createdFiles(String login, String pass1) {
        if (userRepository.createUser(login, encryption.action(pass1))) {
            GlobalVariable.setCurrentUser(new UserEntity(login, pass1));
        } else {
            grammar.write("К сожалению произошла критическая ошибка при создании логина, пожалуйста" +
                    " перезайдите в игру и попробуйте снова");
        }

    }
}
