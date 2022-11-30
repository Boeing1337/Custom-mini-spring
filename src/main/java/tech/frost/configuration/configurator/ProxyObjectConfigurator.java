package tech.frost.configuration.configurator;

import tech.frost.configuration.infrastucture.ApplicationContext;

public interface ProxyObjectConfigurator {
    <T> T configure(T t, ApplicationContext context);
}
