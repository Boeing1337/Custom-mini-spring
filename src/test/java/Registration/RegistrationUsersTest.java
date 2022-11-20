package Registration;

import dao.UserRepository;
import org.junit.jupiter.api.Test;

class RegistrationUsersTest {
    RegistrationUsers registration = new RegistrationUsers(new UserRepository());
    String login = "ArtiWell";
    String pass1 = "qwerty";
    String pass2 = "qwerty";

    @Test
    void registrationUser() {
        registration.registrationUser(login, pass1, pass2);
    }



}