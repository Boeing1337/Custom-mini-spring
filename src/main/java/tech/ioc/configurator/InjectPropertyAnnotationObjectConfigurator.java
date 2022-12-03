package tech.ioc.configurator;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import tech.ioc.ApplicationContext;
import tech.ioc.annotations.InjectProperty;
import tech.ioc.configurator.interfaces.ObjectConfigurator;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.toMap;

/**
 * Подставляет в поля класса значения из конфигурационного файла. Работает в паре с аннотацией {@link InjectProperty}
 */
@Log4j2
public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        String url = getClass().getClassLoader().getResource("application.txt").getPath().replace("%20", " ");
        properties = readAllLines(new File(url).toPath())
                .stream()
                .map(line -> line.split("="))
                .collect(toMap(e -> e[0], e -> e[1]));
    }

    @SneakyThrows
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String key = annotation.value();
                if (key.isEmpty()) {
                    key = field.getName();
                }
                if (!properties.containsKey(key)) {
                    log.warn("Не найдена настройка для поля: {} класса: {}", field.getName(), t.getClass());
                }
                field.setAccessible(true);
                field.set(t, properties.get(key));
            }
        }
    }
}
