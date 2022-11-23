package Registration;

import dao.UserRepository;

public class RegistrationUsers {
    private final UserRepository userRepository;

    public RegistrationUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private boolean stoped = true;

    public static void main(String[] args) {

    }

    private boolean createdUserLogin(String login) {
        return userRepository.createUser(login);
    }

    private boolean readFirstPassword(String login, String pass1) {

        if (!"".equals(pass1)) {
            if (userRepository.createPass(login, pass1)) {
                return true;
            } else {
                System.out.println("Пароль не соответствует требованиям");
                return false;
            }
        } else {
            System.out.println("Пароль не соответствует требованиям");
            return false;
        }
    }

    private boolean readDoublePass(String pass1, String pass2) {
        if (pass2.equals(pass1)) {
            return true;
        } else {
            System.out.println("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
            return false;
        }
    }

    public void registrationUser(String LOGIN, String PASS1, String PASS2) {
        while (stoped) {
            if (createdUserLogin(LOGIN)) {
                stoped = false;
            } else {
                System.out.println("Пользователь с таким логином уже существует или логин введен некорректно. " +
                        "Пожалуйста, попробуйте еще раз");
                continue;
            }
            System.out.println("Введите пароль или нажмите 0, чтобы вернуться в предыдущее меню");
            if ("0".equals(PASS1)) {
                continue;
            }
            while (!stoped) {

                if (readFirstPassword(LOGIN, PASS1)) {
                    if (userRepository.createPass(LOGIN, PASS1)) {
                        System.out.println("Ввудите пароль повторно, или нажмите 0, чтоб вернуться в предыдущее меню");
                        if (readDoublePass(PASS1, PASS2)) {
                            stoped = true;
                        } else {
                            System.out.println("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
                        }
                    } else {
                        System.out.println("Пароль не соответствует требованиям");
                    }
                }
            }
            stoped = false;
        }
    }

}
