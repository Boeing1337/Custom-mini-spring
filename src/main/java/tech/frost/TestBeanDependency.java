package tech.frost;

public class TestBeanDependency implements SOMEINT {

    public TestBeanDependency() {
        System.out.println("beanDepCreated");
    }

    @Override
    public void print() {
        System.out.println("message");
    }
}
