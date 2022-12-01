package tech.wg;

import tech.ioc.infrastucture.Application;
import tech.ioc.see_here.TestBeanPrinter;

public class WordApplication {

    public static void main(String[] args) {
        Application.run("tech").getObject(TestBeanPrinter.class).print("");
    }

}
