package tech.ioc.configurator;

import lombok.SneakyThrows;
import tech.ioc.ApplicationContext;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.configurator.interfaces.ObjectConfigurator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Инициализирует поля {@link Component}'ов другими {@link Component}'тами.
 * Работает в паре с аннотацией {@link InjectObject}
 */
public class InjectObjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(InjectObject.class)) {
                continue;
            }
            InjectObject annotation = field.getAnnotation(InjectObject.class);
            field.setAccessible(true);
            Object bean;
            if (field.getType() == List.class) {
                bean = context.getObjects(
                        (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
            } else if (!annotation.value().isEmpty()) {
                bean = context.getObject(annotation.value());
            } else {
                bean = context.getObject(field.getType());
            }
            field.set(t, bean);
        }
    }
}
