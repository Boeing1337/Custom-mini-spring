package tech.frost.configuration.configurator;

import tech.frost.configuration.configurator.interfaces.InjectObject;
import tech.frost.configuration.infrastucture.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class InjectObjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t, ApplicationContext context) {
        try {
            for (Field field : t.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(InjectObject.class)) {
                    continue;
                }
                field.setAccessible(true);
                Object bean;
                if (field.getType() == List.class) {
                    bean = context.getObjects(
                            (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]
                    );
                } else {
                    bean = context.getObject(field.getType());
                }
                field.set(t, bean);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
