package tech.frost;

import tech.frost.configuration.infrastucture.Component;

@Component
public class TestBeanDependency implements SOMEINT {

    public TestBeanDependency() {
        System.out.println("beanDepCreated");
    }

    @Override
    public void print() {
        System.out.println("message");
    }
}
