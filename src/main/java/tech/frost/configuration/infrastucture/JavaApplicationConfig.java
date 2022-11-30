package tech.frost.configuration.infrastucture;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.*;

public class JavaApplicationConfig implements ApplicationConfig {
    private final Reflections scanner;
    private final Map<Class<?>, List<Class<?>>> newCache;

    JavaApplicationConfig(String packageToScan, Map<Class, Class> cache) {
        this.scanner = new Reflections(packageToScan, Scanners.SubTypes.filterResultsBy(e -> true));
        newCache = new HashMap<>();
        Set<Class<?>> subTypesOf = scanner.getSubTypesOf(Object.class);
        subTypesOf.forEach((Class<?> c) -> {
            if (c.isAnnotationPresent(Component.class)) {
                newCache.computeIfAbsent(c, e2 -> new ArrayList<>());
                newCache.get(c).add(c);
                for (Class<?> intrfc : c.getInterfaces()) {
                    newCache.computeIfAbsent(intrfc, c2 -> new ArrayList<>());
                    newCache.get(intrfc).add(c);
                }
            }
        });
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> beanType) {
        return (Class<? extends T>) newCache.get(beanType).get(0);
    }

    @Override
    public <T> List<Class<? extends T>> getImplClasses(Class<T> beanType) {
        List<Class<? extends T>> result = new ArrayList<>();
        newCache.get(beanType).forEach(e -> result.add((Class<? extends T>) e));
        return result;
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
