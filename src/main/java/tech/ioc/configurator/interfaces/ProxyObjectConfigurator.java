package tech.ioc.configurator.interfaces;

import tech.ioc.ApplicationContext;
import tech.ioc.annotations.Component;
import tech.ioc.infrastucture.BeanContainer;

/**
 * Реализовав этот интерфейс вы можете добавить свою настройку компонентов.
 * Все конфигураторы применяются ко всем {@link Component}
 * Отличается от {@link ObjectConfigurator} тем что оборачивает настоящий объект свойм
 */
public interface ProxyObjectConfigurator {
    void configure(BeanContainer container, ApplicationContext context);
}
