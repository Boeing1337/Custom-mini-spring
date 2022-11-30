package tech.frost.configuration.infrastucture;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    private final ApplicationConfig config;
    @Setter
    private ObjectFactory factory;

    ApplicationContext(ApplicationConfig config) {
        this.config = config;
    }


    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T object = factory.createBean(implClass);

        cache.put(type, object);

        return object;
    }

    public <T> List<Object> getObjects(Class<T> type) {
        List<Class<? extends T>> implClasses = config.getImplClasses(type);
        List<Object> list = new ArrayList<>();
        implClasses.forEach(e -> list.add(getObject((Class<? extends T>) e)));
        return list;
    }
}
