package tech.ioc.infrastucture;

import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;
import tech.ioc.annotations.Component;
import tech.ioc.dto.BeanContainer;
import tech.ioc.infrastucture.interfaces.ApplicationConfig;
import tech.ioc.infrastucture.interfaces.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.reflections.scanners.Scanners.SubTypes;

/**
 * Сканирует все пакеты и решает какой класс или классы послужат прототипом для экземпляра определенного типа.
 */
@Log4j2
public class JavaApplicationConfig implements ApplicationConfig, Scanner {
    private final Reflections scanner;
    private final Map<Class<?>, List<BeanContainer>> beanListCache = new HashMap<>();
    private final Map<String, BeanContainer> beanStringCache = new HashMap<>();


    JavaApplicationConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan, SubTypes.filterResultsBy(e -> true));
        scanner.getSubTypesOf(Object.class).forEach((Class<?> object) -> {
            if (!object.isAnnotationPresent(Component.class)) {
                return;
            }
            String objName = object.getAnnotation(Component.class).name();
            if (objName.isBlank()) {
                objName = object.getName();
            }
            if (beanStringCache.containsKey(objName)) {
                throw new IllegalStateException(
                        format("Найдено повторение имени %s: %s и %s", objName, object, beanStringCache.get(objName)));
            }
            beanStringCache.put(objName, new BeanContainer(objName, object));
            beanListCache.computeIfAbsent(object, o -> new ArrayList<>()).add(new BeanContainer(objName, object));
            for (Class<?> interfaze : object.getInterfaces()) {
                beanListCache.computeIfAbsent(
                        interfaze, o -> new ArrayList<>()).add(new BeanContainer(objName, object)
                );
            }
        });
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> beanType) {
        List<BeanContainer> candidates = beanListCache.get(beanType);
        if (candidates.size() != 1) {
            candidates = candidates.stream()
                    .filter(e -> e.getImplClass().getAnnotation(Component.class).isMain())
                    .collect(Collectors.toList());
            if (candidates.size() != 1) {
                candidates.forEach(log::error);
                throw new IllegalStateException("Найдено более одной реализации для: " + beanType);
            }
        }
        return (Class<? extends T>) candidates.get(0).getImplClass();
    }

    @Override
    public <T> List<Class<? extends T>> getImplClasses(Class<T> beanType) {
        List<Class<? extends T>> result = new ArrayList<>();
        beanListCache.get(beanType).forEach(e -> result.add((Class<? extends T>) e.getImplClass()));
        return result;
    }

    @Override
    public Map<String, BeanContainer> getAllTypes() {
        return beanStringCache;
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }

}

