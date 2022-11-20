package Registration;

import dao.UserRepository;

public class RegistrationUsers {
    private final UserRepository userRepository;

    public RegistrationUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {

    }

    private boolean createdUserLogin(String login) {
        if (userRepository.createUser(login)) {
            return true;
        } else {
            System.out.println("Пользователь с таким логином уже существует или логин введен некорректно. " +
                    "Пожалуйста, попробуйте еще раз");
            return false;
        }
    }
//    boolean stoped = true;
//    public void registrationUser(String LOGIN, String PASS1, String PASS2) {
//        while (stoped) {
//            if (userRepository.createUser(LOGIN)) {
//                stoped = false;
//            } else {
//                System.out.println("Пользователь с таким логином уже существует или логин введен некорректно. " +
//                        "Пожалуйста, попробуйте еще раз");
//                continue;
//            }
//            while (!stoped) {
//                if (!"".equals(PASS1)) {
//                    if (userRepository.createPass(LOGIN, PASS1)) {
//                        System.out.println("Введите пароль повторно");
//                        if (PASS2.equals(PASS1)) {
//                            stoped = true;
//                        } else {
//                            System.out.println("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
//                        }
//                    } else {
//                        System.out.println("Пароль не соответствует требованиям");
//                    }
//                }
//            }
//            stoped = false;
//        }
//    }

    private boolean writePassword(String login, String pass1, String pass2) {

        if (!"".equals(pass1)) {
            if (userRepository.createPass(login, pass1)) {
                System.out.println("Введите пароль повторно");
                if (pass2.equals(pass1)) {
                    return true;
                } else {
                    System.out.println("Ваш первый и второй пароль разные. Пожалуйста, попробуйте еще раз");
                    return false;
                }
            } else {
                System.out.println("Пароль не соответствует требованиям");
                return false;
            }
        } else {
            return false;
        }
    }
}
