package tech.wg.servise.login;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.annotations.InjectProperty;
import tech.wg.tools.Grammar;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class LoginResolver {

    @InjectObject
    private Grammar grammar;

    @InjectObject
    private List<LoginChecker> checkers;

    @InjectProperty
    private int minLoginLength;

    @InjectProperty
    private int maxLoginLength;

    public Optional<String> resolve() {
        grammar.println(format(
                "В логине допустимы только буквы, цифры и знак _ и длинной от %s до %s знаков"
                , minLoginLength
                , maxLoginLength)
        );
        String userInput = grammar.nextLine();
        StringBuilder error = new StringBuilder();
        checkers.forEach(checker -> {
            if (!checker.check(userInput)) {
                error.append(checker.getError());
            }
        });
        if (error.length() == 0) {
            return Optional.of(userInput);
        } else {
            grammar.println(error.toString());
            return Optional.empty();
        }

    }
}
