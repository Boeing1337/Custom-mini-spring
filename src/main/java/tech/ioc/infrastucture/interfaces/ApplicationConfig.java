package tech.ioc.infrastucture.interfaces;

import org.reflections.Reflections;
import tech.ioc.dto.BeanContainer;

import java.util.List;
import java.util.Map;

public interface ApplicationConfig {
    <T> Class<? extends T> getImplClass(Class<T> type);

    <T> List<Class<? extends T>> getImplClasses(Class<T> type);

    Map<String, BeanContainer> getAllTypes();

    Reflections getScanner();
}
