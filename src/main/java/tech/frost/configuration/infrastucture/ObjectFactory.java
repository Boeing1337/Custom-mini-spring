package tech.frost.configuration.infrastucture;

import lombok.Getter;
import lombok.SneakyThrows;
import tech.frost.configuration.configurator.ObjectConfigurator;
import tech.frost.configuration.configurator.ProxyObjectConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;

public class ObjectFactory {
    private final ApplicationContext context;
    @Getter(value = PACKAGE)
    private final ApplicationConfig config;
    private final List<ObjectConfigurator> simpleObjectConfigurators = new ArrayList<>();
    private final List<ProxyObjectConfigurator> proxyObjectConfigurators = new ArrayList<>();

    @SneakyThrows
    ObjectFactory(ApplicationContext context) {
        this.context = context;
        config = new JavaApplicationConfig("tech.frost", new HashMap<>());
        for (var objConfigurator : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            simpleObjectConfigurators.add(objConfigurator.getDeclaredConstructor().newInstance());
        }
        for (var objConfigurator : config.getScanner().getSubTypesOf(ProxyObjectConfigurator.class)) {
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
