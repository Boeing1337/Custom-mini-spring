package tech.ioc.infrastucture;


import lombok.NoArgsConstructor;
import tech.ioc.infrastucture.resolver.ApplicationPropertiesResolver;

import static lombok.AccessLevel.PRIVATE;

/**
 * Класс через который запускается приложение
 */
@NoArgsConstructor(access = PRIVATE)
public class Application {
    /**
     * @param packageToScan основной пакет с которого начинать сканирование зависимостей
     * @return контекст у которого можно попросить стартовую зависимость для запуска приложения.
     */
    public static ApplicationContext run(String packageToScan) {
        JavaApplicationConfig config = new JavaApplicationConfig(packageToScan);
        ApplicationPropertiesResolver properties = new ApplicationPropertiesResolver(config, "application.properties");
        ApplicationContext context = new ApplicationContext(config, properties);
        ObjectFactory objectFactory = new ObjectFactory(context, config);
        context.setFactory(objectFactory);
        return context.init();
    }
}
