package tech.ioc.infrastucture.resolver;

import lombok.SneakyThrows;
import tech.ioc.casting.FromStringTypeCaster;
import tech.ioc.infrastucture.interfaces.Scanner;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class ApplicationPropertiesResolver implements PropertyResolver {
    private final Map<String, String> properties;
    private final Map<Class<?>, FromStringTypeCaster> casters = new HashMap<>();

    @SneakyThrows
    public ApplicationPropertiesResolver(Scanner scanner, String resource) {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(resource)) {
            properties = stream(new String(resourceAsStream.readAllBytes()).split("\\n|\\r\\n"))
                    .map(line -> line.split("="))
                    .collect(toMap(e -> e[0], e -> e[1]));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        for (var caster : scanner.getScanner().getSubTypesOf(FromStringTypeCaster.class)) {
            FromStringTypeCaster fromStringTypeCaster = caster.getDeclaredConstructor().newInstance();
            if (casters.containsKey(fromStringTypeCaster)) {
                throw new IllegalStateException("Найдено более 1 конвертера для: " + fromStringTypeCaster.fromType());
            }
            casters.put(fromStringTypeCaster.fromType(), fromStringTypeCaster);
        }
    }


    @Override
    public <T> T getPropertyAs(String propName, Class<T> type) {
        String o = properties.get(propName);
        if (o == null) {
            throw new NoPropertyFoundException(propName);
        }
        FromStringTypeCaster caster = casters.get(type);
        if (caster == null) {
            throw new NoPropertyCasterFoundException(type);
        }
        return (T) caster.cast(o);
    }

    @Override
    public String getProperty(String propName) {
        return getPropertyAs(propName, String.class);
    }

    private static class NoPropertyFoundException extends RuntimeException {
        public NoPropertyFoundException(String propName) {
            super(format("Не найдена настройка %s! ", propName));
        }
    }

    private static class NoPropertyCasterFoundException extends RuntimeException {
        public NoPropertyCasterFoundException(Class<?> type) {
            super(format("Не найден конвертер для поля типа: %s! ", type.getName()));
        }
    }
}
