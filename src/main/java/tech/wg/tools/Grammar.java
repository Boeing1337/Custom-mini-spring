package tech.wg.tools;

import tech.ioc.annotations.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class Grammar {
    Scanner scanner = new Scanner(System.in);
    PrintStream printStream = System.out;

    public void println(String value) {
        printStream.println(value);
    }

    public void print(String value) {
        printStream.print(value);
    }

    public void println(int value) {
        printStream.println(value);
    }

    public void println(char value) {
        printStream.println(value);
    }

    public int nextInt() {
        return scanner.nextInt();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public String next() {
        return scanner.next();
    }

    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }
}
