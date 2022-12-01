package tech.ioc.infrastucture;

import org.reflections.Reflections;
import tech.ioc.annotations.Component;

import java.util.*;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;

/**
 * Сканирует все пакеты и решает какой класс или классы послужат прототипом для экземпляра определенного типа.
 */
public class JavaApplicationConfig implements ApplicationConfig {
    private final Reflections scanner;
    private final Map<Class<?>, List<Class<?>>> listCache = new HashMap<>();
    private final Map<String, Class<?>> stringCache = new HashMap<>();
    private final Set<Class<?>> rawObjects;

    JavaApplicationConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan, SubTypes.filterResultsBy(e -> true));
        rawObjects = scanner.getSubTypesOf(Object.class);
        rawObjects.forEach((Class<?> object) -> {
            if (!object.isAnnotationPresent(Component.class)) {
                return;
            }
            String objName = object.getAnnotation(Component.class).name();
            if (objName.isBlank()) {
                objName = object.getName();
            }
            stringCache.put(objName, object);
            listCache.computeIfAbsent(object, o -> new ArrayList<>()).add(object);
            for (Class<?> intrfc : object.getInterfaces()) {
                listCache.computeIfAbsent(intrfc, o -> new ArrayList<>()).add(object);
            }
        });
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> beanType) {
        List<Class<?>> candidates = listCache.get(beanType);
        if (candidates.size() != 1) {
            candidates = candidates.stream()
                    .filter(e -> e.getAnnotation(Component.class).isMain())
                    .collect(Collectors.toList());
            if (candidates.size() != 1) {
                candidates.forEach(System.out::println);
                throw new IllegalStateException("Найдено более одной реализации для: " + beanType);
            }
        }
        return (Class<? extends T>) candidates.get(0);
    }

    @Override
    public Class<?> getImplClassByName(String name) {
        if (!stringCache.containsKey(name)) {
            throw new IllegalStateException("Не найдено реализации для имени компонента: " + name);
        }
        return getImplClass(stringCache.get(name));
    }

    @Override
    public <T> List<Class<? extends T>> getImplClasses(Class<T> beanType) {
        List<Class<? extends T>> result = new ArrayList<>();
        listCache.get(beanType).forEach(e -> result.add((Class<? extends T>) e));
        return result;
    }

    @Override
    public Map<String, Class<?>> getAllTypes() {
        return stringCache;
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }

}
