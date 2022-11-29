package context;

public class GlobalVariable {

    public static String staticLogin;

    public static String getStaticLogin() {
        return staticLogin;
    }

    public static void setStaticLogin(String login) {
        staticLogin = login;
    }
}
