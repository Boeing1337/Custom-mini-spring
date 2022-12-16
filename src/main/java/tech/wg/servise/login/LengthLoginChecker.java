package tech.wg.servise.login;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import static java.lang.String.format;

@Component
public class LengthLoginChecker implements LoginChecker {

    @InjectProperty
    private int minLoginLength;

    @InjectProperty
    private int maxLoginLength;

    @Override
    public boolean check(String login) {
        return login.length() <= maxLoginLength && login.length() >= minLoginLength;
    }

    @Override
    public String getError() {
        return format("Длинна логина должна быть в диапазоне от %s до %s", maxLoginLength, maxLoginLength);
    }
}
