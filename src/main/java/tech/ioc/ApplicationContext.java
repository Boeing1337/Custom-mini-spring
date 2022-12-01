package tech.ioc;

import lombok.Setter;
import tech.ioc.annotations.Component;
import tech.ioc.infrastucture.ApplicationConfig;
import tech.ioc.infrastucture.ObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Контекст приложения. Все созданные {@link Component}'ы хранятся тут.
 * Предоставляет методы для получения {@link Component}'ов. Не рекомендуется использовать их
 * для инициализации зависимостей самостоятельно
 */
public class ApplicationContext {
    private final Map<Class<?>, Object> listCache = new ConcurrentHashMap<>();
    private final Map<String, Object> stringCache = new ConcurrentHashMap<>();
    private final ApplicationConfig config;
    @Setter
    private ObjectFactory factory;

    public ApplicationContext(ApplicationConfig config) {
        this.config = config;
    }

    /**
     * Возвращает {@link Component} по заданному имени компонента. Подробнее в аннотации {@link Component}
     *
     * @param type тип компонента
     * @return созданный и настроенный объект.
     */
    public <T> T getObject(Class<T> type) {
        if (listCache.containsKey(type)) {
            return (T) listCache.get(type);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T object = factory.createBean(implClass);

        listCache.put(type, object);
        listCache.put(object.getClass(), object);

        return object;
    }

    /**
     * Возвращает {@link Component} по заданному имени компонента. Подробнее в аннотации {@link Component}
     *
     * @param name имя компонента
     * @return созданный и настроенный объект. Кастить тип придётся самостоятельно
     */
    public Object getObject(String name) {
        if (stringCache.containsKey(name)) {
            return stringCache.get(name);
        }

        Class<?> implClass = config.getImplClassByName(name);

        Object object = getObject(implClass);

        stringCache.put(name, object);

        return object;
    }

    /**
     * Возвращает {@link Component}'ы по заданному имени компонента. Подробнее в аннотации {@link Component}
     *
     * @param type тип компонентов
     * @return созданные и настроенные объекты. Не рекоменуется использовать этот метод для логики приложения
     */
    public <T> List<Object> getObjects(Class<T> type) {
        List<Class<? extends T>> implClasses = config.getImplClasses(type);
        List<Object> list = new ArrayList<>();
        implClasses.forEach(e -> list.add(getObject((Class<? extends T>) e)));
        return list;
    }

}
