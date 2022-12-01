package tech.ioc.see_here;

import tech.ioc.annotations.Component;

@Component(isMain = true)
public class TestBeanDependency1 implements SomeInterface {

    public TestBeanDependency1() {
        System.out.println("TestBeanDependency1 created");
    }

    @Override
    public void print(String msg) {
        System.out.println(msg + "FROM TestBeanDependency1");
    }
}
