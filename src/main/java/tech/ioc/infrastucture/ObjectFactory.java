package tech.ioc.infrastucture;

import lombok.SneakyThrows;
import tech.ioc.configurator.interfaces.ObjectConfigurator;
import tech.ioc.configurator.interfaces.ProxyObjectConfigurator;
import tech.ioc.dto.BeanContainer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

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
        for (var configurator : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            simpleObjectConfigurators.add(
                    configurator.getConstructor(ApplicationContext.class).newInstance(context));
        }
        for (var objConfigurator : config.getScanner().getSubTypesOf(ProxyObjectConfigurator.class)) {
            proxyObjectConfigurators.add(
                    objConfigurator.getConstructor(ApplicationContext.class).newInstance(context));
        }
    }

    @SneakyThrows
    public void createBean(BeanContainer beanContainer) {
        Object t = getNoArgConstructorOrThrow(beanContainer).newInstance();
        beanContainer.initBean(t);
        for (ObjectConfigurator configurator : simpleObjectConfigurators) {
            configurator.configure(t, context);
        }
        for (ProxyObjectConfigurator configurator : proxyObjectConfigurators) {
            configurator.configure(beanContainer, context);
        }
    }

    private Constructor<?> getNoArgConstructorOrThrow(BeanContainer beanContainer) {
        return Arrays.stream(beanContainer.getImplClass().getConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findFirst()
                .orElseThrow(() -> new NoArgConstructorMissingException(beanContainer.getImplClass()));
    }

    private static class NoArgConstructorMissingException extends RuntimeException {
        public NoArgConstructorMissingException(Class<?> object) {
            super(format("Нет конструктора без аргументов для объекта %s! ", object.getName()));
        }
    }
}
