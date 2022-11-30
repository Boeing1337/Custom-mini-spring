package tech.frost.configuration.infrastucture;

import org.reflections.Reflections;

import java.util.*;

public class JavaApplicationConfig implements ApplicationConfig {
    private final Reflections scanner;
    private final Map<Class, Class> cache;
    private final Map<Class, List<String>> multiCache = new HashMap<>();

    JavaApplicationConfig(String packageToScan, Map<Class, Class> cache) {
        this.scanner = new Reflections(packageToScan);
        this.cache = cache;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> beanType) {
        return cache.computeIfAbsent(beanType, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(beanType);
            if (classes.size() != 1) {
                return null;
                //throw new IllegalStateException(aClass + " has 0 or more than 1 implementations");
            }
            return classes.iterator().next();
        });
    }

    @Override
    public <T> List<String> getImplClasses(Class<T> beanType) {
        return multiCache.computeIfAbsent(beanType, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(beanType);
            ArrayList<String> result = new ArrayList<>();
            classes.iterator().forEachRemaining(
                    e -> result.add(e.getName())
            );
            return result;
        });
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
