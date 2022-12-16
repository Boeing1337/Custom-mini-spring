package tech.wg.servise.login;

public interface LoginChecker {


    boolean check(String login);

    String getError();
}
