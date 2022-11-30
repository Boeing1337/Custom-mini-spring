package tech.frost.configuration.configurator;

import tech.frost.configuration.infrastucture.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
