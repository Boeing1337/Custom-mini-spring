package tech.ioc.infrastucture;

import lombok.Getter;
import lombok.SneakyThrows;
import tech.ioc.ApplicationContext;
import tech.ioc.configurator.interfaces.ObjectConfigurator;
import tech.ioc.configurator.interfaces.ProxyObjectConfigurator;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;

/**
 * Создает объекты и прогоняет их через все {@link ObjectConfigurator} и {@link ProxyObjectConfigurator}
 */
public class ObjectFactory {
    private final ApplicationContext context;
    @Getter(value = PACKAGE)
    private final ApplicationConfig config;
    private final List<ObjectConfigurator> simpleObjectConfigurators = new ArrayList<>();
    private final List<ProxyObjectConfigurator> proxyObjectConfigurators = new ArrayList<>();

    @SneakyThrows
    ObjectFactory(ApplicationContext context, JavaApplicationConfig config) {
        this.context = context;
        this.config = config;
        for (var objConfigurator : this.config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            simpleObjectConfigurators.add(objConfigurator.getDeclaredConstructor().newInstance());
        }
        for (var objConfigurator : this.config.getScanner().getSubTypesOf(ProxyObjectConfigurator.class)) {
            proxyObjectConfigurators.add(objConfigurator.getDeclaredConstructor().newInstance());
        }

    }

    @SneakyThrows
    public <T> T createBean(Class<T> implClass) {

        T t = implClass.getDeclaredConstructor().newInstance();

        for (ObjectConfigurator configurator : simpleObjectConfigurators) {
            configurator.configure(t, context);
        }
        for (ProxyObjectConfigurator configurator : proxyObjectConfigurators) {
            t = configurator.configure(t, context);
        }
        return t;

    }
}
