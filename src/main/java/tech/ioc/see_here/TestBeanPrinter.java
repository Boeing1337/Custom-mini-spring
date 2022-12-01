package tech.ioc.see_here;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.annotations.InjectProperty;

import java.util.List;

@Component
public class TestBeanPrinter {

    @InjectProperty
    private String value;

    @InjectObject
    private SomeInterface testBeanDependency;
    @InjectObject
    private List<SomeInterface> testBeanDependencies;

    public TestBeanPrinter() {
        System.out.println("TestBeanPrinter created");
    }

    public void print(String message) {
        testBeanDependency.print(message + "from dependency 1");
        testBeanDependencies.forEach(e -> e.print("calling list of dependencies"));
        System.out.println("Injected value: " + value);
    }
}
