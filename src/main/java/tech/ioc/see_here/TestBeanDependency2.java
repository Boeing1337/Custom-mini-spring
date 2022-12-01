package tech.ioc.see_here;

import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectObject;

@Component
public class TestBeanDependency2 implements SomeInterface {

    @InjectObject
    private SomeInterface someInterface;

    public TestBeanDependency2() {
        System.out.println("TestBeanDependency2 created");
    }

    @Override
    public void print(String msg) {
        System.out.println(msg + "FROM TestBeanDependency2");
        someInterface.print("To nested someInterface FROM TestBeanDependency2");
    }
}
