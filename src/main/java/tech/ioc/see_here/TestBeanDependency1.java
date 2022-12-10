package tech.ioc.see_here;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;

@Log4j2
@Component(isMain = true)
public class TestBeanDependency1 implements SomeInterface {

    @InjectObject(value = "testPrinter")
    private TestBeanPrinter testBeanPrinter;

    public TestBeanDependency1() {
        log.info("TestBeanDependency1 created");
    }

    @Override
    public void print(String msg) {
        log.info(msg + "FROM TestBeanDependency1");
    }
}
