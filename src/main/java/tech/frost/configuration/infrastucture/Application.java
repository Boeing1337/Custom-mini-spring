package tech.frost.configuration.infrastucture;


import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> beanCache) {
        JavaApplicationConfig config = new JavaApplicationConfig(packageToScan, beanCache);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
