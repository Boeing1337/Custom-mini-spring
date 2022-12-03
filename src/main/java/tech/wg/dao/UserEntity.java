package tech.wg.dao;

import lombok.Getter;

@Getter
public class UserEntity {
    private final String login;
    private final String pass;


    public UserEntity(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

}
