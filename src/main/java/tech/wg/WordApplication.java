package tech.wg;

import tech.ioc.infrastucture.Application;
import tech.ioc.see_here.TestBeanPrinter;
import tech.wg.servise.MainMenu;

public class WordApplication {


    public static void main(String[] args) {
        if (args.length != 0 && "test".equals(args[0])) {
            Application.run("tech").getObject(TestBeanPrinter.class).print("");
        } else {
            Application.run("tech").getObject(MainMenu.class).startMainMenu();
        }
    }

}
