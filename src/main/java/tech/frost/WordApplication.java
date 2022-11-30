package tech.frost;

import tech.frost.configuration.infrastucture.Application;
import tech.frost.configuration.infrastucture.ApplicationContext;

import java.util.concurrent.ConcurrentHashMap;

public class WordApplication {

    public static void main(String[] args) {
        ApplicationContext context = Application.run("tech.frost", new ConcurrentHashMap<>());
        context.getObject(TestBeanPrinter.class).print("test");
    }

}
