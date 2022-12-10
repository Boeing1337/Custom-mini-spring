package tech.ioc.configurator;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.InjectProperty;
import tech.ioc.configurator.interfaces.ObjectConfigurator;
import tech.ioc.infrastucture.ApplicationContext;
import tech.ioc.infrastucture.resolver.PropertyResolver;

import java.lang.reflect.Field;

import static java.lang.String.format;

/**
 * Подставляет в поля класса значения из конфигурационного файла. Работает в паре с аннотацией {@link InjectProperty}
 */
@Log4j2
public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private final PropertyResolver resolver;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator(ApplicationContext context) {
        this.resolver = context.getPropertyResolver();
    }

    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String key = annotation.value();
                if (key.isEmpty()) {
                    key = field.getName();
                }
                field.setAccessible(true);
                try {
                    field.set(t, resolver.getPropertyAs(key, field.getType()));
                } catch (RuntimeException | IllegalAccessException e) {
                    throw new InjectPropertyException(key, field, t, e);
                }
            }
        }
    }

    private static class InjectPropertyException extends RuntimeException {
        public InjectPropertyException(String name, Field field, Object t, Throwable cause) {
            super(
                    format("%s Не удалось вставить проперти %s для поля %s компонента %s! ",
                            cause.getMessage(),
                            name,
                            field.getName(),
                            t.getClass().getName())
                    , cause
            );

        }
    }
}
