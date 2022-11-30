package tech.frost;

import tech.frost.configuration.infrastucture.Component;

@Component
public class TestBeanDependency2 implements SOMEINT {

    public TestBeanDependency2() {
        System.out.println("beanDepCreated");
    }

    public void print() {
        System.out.println("message2");
    }
}
