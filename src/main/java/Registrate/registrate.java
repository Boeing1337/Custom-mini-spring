package Registrate;

import dao.UserRepository;

import java.util.Scanner;

public class registrate {
    public static boolean stoped = true;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (stoped) {
            System.out.println("Введите логин");
            String login = scanner.nextLine();
            if (UserRepository.createUser(login)) {

                stoped = false;
            } else {
                System.out.println("Пользователь с таким логином уже существует или логин введен некорректно. " +
                        "Пожалуйста, попробуйте еще раз");
                continue;
            }
            while (!stoped) {
                System.out.println("Введите пароль");
                String pass = scanner.nextLine();
                if (!"".equals(pass)) {
                    if (UserRepository.createPass(login, pass)) {
                        System.out.println("Введите пароль повторно");
                        String passRe = scanner.nextLine();
                        if (passRe.equals(pass)) {
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
