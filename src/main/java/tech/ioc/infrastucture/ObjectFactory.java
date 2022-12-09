package tech.ioc.infrastucture;

import lombok.SneakyThrows;
import tech.ioc.ApplicationContext;
import tech.ioc.configurator.interfaces.ObjectConfigurator;
import tech.ioc.configurator.interfaces.ProxyObjectConfigurator;

import java.util.ArrayList;
import java.util.List;

/**
 * Создает объекты и прогоняет их через все {@link ObjectConfigurator} и {@link ProxyObjectConfigurator}
 */
public class ObjectFactory {
    private final ApplicationContext context;
    private final List<ObjectConfigurator> simpleObjectConfigurators = new ArrayList<>();
    private final List<ProxyObjectConfigurator> proxyObjectConfigurators = new ArrayList<>();

    @SneakyThrows
    ObjectFactory(ApplicationContext context, JavaApplicationConfig config) {
        this.context = context;
        for (var objConfigurator : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            simpleObjectConfigurators.add(objConfigurator.getDeclaredConstructor().newInstance());
        }
        for (var objConfigurator : config.getScanner().getSubTypesOf(ProxyObjectConfigurator.class)) {
            proxyObjectConfigurators.add(objConfigurator.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public void createBean(BeanContainer beanContainer) {
        Object t = beanContainer.getImplClass().getDeclaredConstructor().newInstance();
        beanContainer.initBean(t);
        for (ObjectConfigurator configurator : simpleObjectConfigurators) {
            configurator.configure(t, context);
        }
        for (ProxyObjectConfigurator configurator : proxyObjectConfigurators) {
            configurator.configure(beanContainer, context);
        }
    }
}
