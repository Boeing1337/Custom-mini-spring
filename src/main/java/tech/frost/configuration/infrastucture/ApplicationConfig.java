package tech.frost.configuration.infrastucture;

import org.reflections.Reflections;

import java.util.List;

public interface ApplicationConfig {
    <T> Class<? extends T> getImplClass(Class<T> type);

    <T> List<String> getImplClasses(Class<T> type);

    Reflections getScanner();
}
