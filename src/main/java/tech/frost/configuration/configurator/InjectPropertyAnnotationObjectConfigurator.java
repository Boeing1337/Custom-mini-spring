package tech.frost.configuration.configurator;

import lombok.SneakyThrows;
import tech.frost.configuration.configurator.interfaces.InjectProperty;
import tech.frost.configuration.infrastucture.ApplicationContext;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {

        String url = getClass().getClassLoader().getResource("application.properties").getPath().replaceAll("%20", " ");
        List<String> lines = Files.readAllLines(new File(url).toPath());
        properties = lines.stream().map(line -> line.split("=")).collect(toMap(e -> e[0], e -> e[1]));
    }

    @SneakyThrows
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String value = annotation.value().isEmpty() ? properties.get(field.getName()) : properties.get(annotation.value());
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
