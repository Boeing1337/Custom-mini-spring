package tech.ioc.configurator;

import tech.ioc.annotations.InjectObject;
import tech.ioc.dto.BeanContainer;
import tech.ioc.infrastucture.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static java.lang.String.format;

public class BeanWeaver {

    public void weave(BeanContainer beanContainer, ApplicationContext context) {
        for (Field field : beanContainer.getImplClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(InjectObject.class)) {
                continue;
            }
            try {
                Object bean = getBeanForInjection(field, context);
                field.set(beanContainer.getOriginal(), bean);
            } catch (IllegalStateException e) {
                throw new NoBeanFoundForInjectionException(field.getType(), beanContainer.getImplClass(), e);
            } catch (IllegalAccessException e) {
                throw new IllegalCallerException(e);
            }
        }
    }

    private Object getBeanForInjection(Field field, ApplicationContext context) {
        InjectObject annotation = field.getAnnotation(InjectObject.class);
        field.setAccessible(true);
        if (field.getType() == List.class) {
            return context.getObjects(
                    (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
        } else if (annotation.value().isEmpty()) {
            return context.getObject(field.getType());
        } else {
            return context.getObject(annotation.value());
        }
    }

    private static class NoBeanFoundForInjectionException extends RuntimeException {

        public NoBeanFoundForInjectionException(Class<?> field, Class<?> injectTarget, Throwable cause) {
            super(format("Не найдена зависимость типа: %s для компонента: %s", field, injectTarget), cause);
        }
    }
}
