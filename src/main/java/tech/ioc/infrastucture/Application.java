package tech.ioc.infrastucture;


import lombok.NoArgsConstructor;
import tech.ioc.ApplicationContext;

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
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context, config);
        context.setFactory(objectFactory);
        return context.init();
    }
}
