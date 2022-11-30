package tech.frost;

import tech.frost.configuration.configurator.interfaces.InjectObject;
import tech.frost.configuration.configurator.interfaces.InjectProperty;

import java.util.List;

public class TestBeanPrinter {

    @InjectProperty
    private String value;
    @InjectObject
    private SOMEINT testBeanDependency;
    @InjectObject
    private List<SOMEINT> testBeanDependencies;

    public TestBeanPrinter() {
        System.out.println("created");
    }

    public void print(String message) {
        // testBeanDependency.print();
        testBeanDependencies.forEach(e -> e.print());
        System.out.println(value + " value");
    }
}
