package tech.wg.servise.login;

public class CharactersLoginChecker implements LoginChecker {
    @Override
    public boolean check(String login) {
        return login.matches("[ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ\\w]+");
    }

    @Override
    public String getError() {
        return "Найдены недопустимые символы в логине. Разрешены: буквы, цифры, и знак _ .";
    }
}
