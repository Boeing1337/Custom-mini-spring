package tech.ioc.infrastucture;

import lombok.Getter;
import lombok.Setter;
import tech.ioc.annotations.Component;
import tech.ioc.configurator.BeanWeaver;
import tech.ioc.dto.BeanContainer;
import tech.ioc.infrastucture.interfaces.ApplicationConfig;
import tech.ioc.infrastucture.resolver.ApplicationPropertiesResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Контекст приложения. Все созданные {@link Component}'ы хранятся тут.
 * Предоставляет методы для получения {@link Component}'ов. Не рекомендуется использовать их
 * для инициализации зависимостей самостоятельно
 */
public class ApplicationContext {

    private final ApplicationConfig config;
    @Getter
    private final ApplicationPropertiesResolver propertyResolver;
    private final Map<Class<?>, BeanContainer> beanCacheByClass = new ConcurrentHashMap<>();
    private final Map<String, BeanContainer> beanCacheByName = new ConcurrentHashMap<>();
    @Setter
    private ObjectFactory factory;

    public ApplicationContext(ApplicationConfig config, ApplicationPropertiesResolver propertyResolver) {
        this.config = config;
        this.propertyResolver = propertyResolver;
    }

    /**
     * Возвращает {@link Component} по заданному имени компонента. Подробнее в аннотации {@link Component}
     *
     * @param type тип компонента
     * @return созданный и настроенный объект.
     */
    public <T> T getObject(Class<T> type) {
        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        if (!beanCacheByClass.containsKey(implClass)) {
            throw new IllegalStateException("Нет зависимости для: " + implClass);
        }
        return (T) beanCacheByClass.get(implClass).getProxy();
    }

    /**
     * Возвращает {@link Component} по заданному имени компонента. Подробнее в аннотации {@link Component}
     *
     * @param name имя компонента
     * @return созданный и настроенный объект. Кастить тип придётся самостоятельно
     */
    public Object getObject(String name) {
        if (!beanCacheByName.containsKey(name)) {
            throw new IllegalStateException("Нет зависимости с именем: " + name);
        }
        return beanCacheByName.get(name).getProxy();
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

    public ApplicationContext init() {
        BeanWeaver beanWeaver = new BeanWeaver();
        Collection<BeanContainer> values = config.getAllTypes().values();
        values.forEach((BeanContainer beanContainer) -> {
            factory.createBean(beanContainer);
            beanCacheByClass.put(beanContainer.getImplClass(), beanContainer);
            beanCacheByName.put(beanContainer.getBeanName(), beanContainer);
        });
        values.forEach((BeanContainer beanContainer) -> beanWeaver.weave(beanContainer, this));
        return this;
    }
}
