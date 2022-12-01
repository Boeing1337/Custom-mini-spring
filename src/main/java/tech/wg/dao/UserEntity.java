package tech.wg.dao;

public class UserEntity {
    private final String login;
    private final String pass;


    public UserEntity(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
