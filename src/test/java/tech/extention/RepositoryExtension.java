package tech.extention;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.MissingResourceException;

import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.toMap;

@Log4j2
public class RepositoryExtension implements TestInstancePostProcessor {
    private final Map<String, String> properties;

    @SneakyThrows
    public RepositoryExtension() {
        String url = getClass().getClassLoader().getResource("application-test.properties").getPath().replace("%20", " ");
        properties = readAllLines(new File(url).toPath())
                .stream()
                .map(line -> line.split("="))
                .collect(toMap(e -> e[0], e -> e[1]));
    }

    @SneakyThrows
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        for (Field testField : testInstance.getClass().getDeclaredFields()) {
            testField.setAccessible(true);
            Object fieldValue = testField.get(testInstance);
            if (!fieldValue.getClass().isAnnotationPresent(Component.class)) {
                return;
            }
            for (Field field : fieldValue.getClass().getDeclaredFields()) {
                InjectProperty annotation = field.getAnnotation(InjectProperty.class);
                if (annotation != null) {
                    String key = annotation.value();
                    if (key.isEmpty()) {
                        key = field.getName();
                    }
                    if (!properties.containsKey(key)) {
                        throw new MissingResourceException("Не найдена конфигурация", testInstance.getClass().getName(), key);
                    }
                    field.setAccessible(true);
                    field.set(fieldValue, properties.get(key));
                }
            }
        }
    }

}
