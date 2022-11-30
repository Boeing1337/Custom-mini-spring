package tech.frost;

public class TestBeanDependency2 implements SOMEINT {

    public TestBeanDependency2() {
        System.out.println("beanDepCreated");
    }

    public void print() {
        System.out.println("message2");
    }
}
