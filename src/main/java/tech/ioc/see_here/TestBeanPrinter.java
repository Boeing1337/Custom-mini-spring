package tech.ioc.see_here;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;
import tech.ioc.annotations.InjectProperty;

import java.util.List;

@Log4j2
@Component
public class TestBeanPrinter {

    @InjectProperty
    private String value;

    @InjectObject
    private SomeInterface testBeanDependency;
    @InjectObject
    private List<SomeInterface> testBeanDependencies;

    public TestBeanPrinter() {
        log.info("TestBeanPrinter created");
    }

    public void print(String message) {
        testBeanDependency.print(message + "from dependency 1");
        testBeanDependencies.forEach(e -> e.print("calling list of dependencies"));
        log.info("Injected value: " + value);
    }
}
