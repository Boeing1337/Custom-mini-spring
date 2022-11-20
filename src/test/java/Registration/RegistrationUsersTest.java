package Registration;

import dao.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationUsersTest {
    RegistrationUsers registration = new RegistrationUsers(new UserRepository());
    String login = "ArtiWell";
    String pass1 = "qwerty";
    String pass2 = "qwerty";

    @Test
    void writePassword() {

        Assertions.assertTrue(registration.writePassword(login, pass1, pass2));
    }

    @Test
    void createdUserLogin() {

        Assertions.assertTrue(registration.createdUserLogin(login));

    }


}