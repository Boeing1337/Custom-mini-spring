package tech.wg.dao;

import java.io.PrintStream;
import java.util.Scanner;

public class Grammar {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream printStream = System.out;


    public void write(String value) {
        printStream.println(value);
    }

    public void write(int value) {
        printStream.println(value);
    }

    public void write(char value) {
        printStream.println(value);
    }

    public int readInt() {
        return scanner.nextInt();
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public String readToken() {
        return scanner.next();
    }

    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }
}
