package tech.ioc.infrastucture;

import org.reflections.Reflections;

import java.util.List;
import java.util.Map;

public interface ApplicationConfig {
    <T> Class<? extends T> getImplClass(Class<T> type);

    Class<?> getImplClassByName(String name);

    <T> List<Class<? extends T>> getImplClasses(Class<T> type);

    Map<String, Class<?>> getAllTypes();

    Reflections getScanner();
}
